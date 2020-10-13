package Main;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLconfig {

    private int ColumnIndex;

    public int GetColumnIndex()
    {
        return ColumnIndex;
    }



}
