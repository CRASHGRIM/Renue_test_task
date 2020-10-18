package Common;

import java.io.IOException;

public interface IinOut {

    public String ReadLine() throws IOException;

    public void WriteLine(String line);

    public void Close() throws IOException;
}
