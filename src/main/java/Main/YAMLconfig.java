package Main;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "main")
public class YAMLconfig {

    private int column;

    public int getColumn()
    {
        return column;
    }

    public void setColumn(int column) {this.column = column; }



}
