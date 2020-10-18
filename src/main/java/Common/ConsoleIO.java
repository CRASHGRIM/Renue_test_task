package Common;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ConsoleIO implements IinOut {

    private BufferedReader br;

    public ConsoleIO()
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        this.br = new BufferedReader(isr);
    }

    public void WriteLine(String line)
    {
        System.out.println(line);
    }

    public String ReadLine() throws IOException {
        return br.readLine();
    }

    public void Close() throws IOException {
        br.close();
    }

}
