package Main;


import Common.YamlPropertyLoaderFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "main")
//@PropertySource(value = "application.yml", factory = YamlPropertyLoaderFactory.class)
public class YAMLconfig {

    private int column;
    private String filename;

    public int getColumn()
    {
        return column;
    }

    public void setColumn(int column) {this.column = column; }

    public String getFilename() {return filename;}

    public void setFilename(String filename) {this.filename = filename;}



}
