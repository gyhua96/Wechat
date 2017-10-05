package Server;
import com.sun.xml.internal.ws.wsdl.writer.document.PortType;

import javax.swing.plaf.basic.BasicToolBarUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

public class TCPServer {
    private int PORT=8088;
    private Selector selector=null;
    private ServerSocketChannel ssc=null;
    public TCPServer() throws IOException {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().setReuseAddress(true);
        for (int i = 0; i < 5; i++) {
            try {
                ssc.socket().bind(new InetSocketAddress(PORT));
                System.out.print(PORT);
                break;
            } catch (IOException e) {
                PORT++;
            }
        }
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }
// File transfor
    public void listen() throws Exception {
        while(selector.select()>0) {
            System.out.print(" sonething ");
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                ByteBuffer buf=(ByteBuffer) key.attachment();
                it.remove();
                //TODO handle key
                if(key.isAcceptable()){
                    SocketChannel socket=((ServerSocketChannel) key.channel()).accept();
                    socket.configureBlocking(false);
                    //key.interestOps(SelectionKey.OP_WRITE);
                    socket.register(key.selector(),SelectionKey.OP_WRITE, ByteBuffer.allocate(2048));
                    System.out.print(" accepted ");
                }
                if(key.isReadable()){

                    int byteReadkey=((SocketChannel) key.channel()).read(buf);
                    buf.flip();
                    System.out.print("read"+byteBufferToString(buf)+" "+buf.get(0));
                    if(byteReadkey==-1){
                        key.channel().close();
                    }else if(byteReadkey>0){
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                }
                if(key.isValid()&&key.isWritable()){
                    SocketChannel socket=(SocketChannel) key.channel();
                    buf.flip();
                    String res=" Hello ya! ";
                    buf=ByteBuffer.wrap(res.getBytes());
                    socket.write(buf);
                    System.out.print(" write ");
                    key.channel().close();
                }
                //buf.compact();
                //System.out.print("Something happened.");
            }
        }

    }
    public static String byteBufferToString(ByteBuffer buf)  {
        CharBuffer charBuffer = null;
        Charset charSet= Charset.forName("UTF-8");
        CharsetDecoder decode=charSet.newDecoder();
        try {
            charBuffer=decode.decode(buf);
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        buf.flip();
        return charBuffer.toString();

    }
}
