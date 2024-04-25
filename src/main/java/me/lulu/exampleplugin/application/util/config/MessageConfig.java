package me.lulu.exampleplugin.application.util.config;

import com.google.common.collect.Lists;
import org.bukkit.configuration.file.YamlConfiguration;

import java.nio.file.Path;
import java.util.List;

public class MessageConfig {

    private final Path path;
    private final GetDefaultResource getDefaultResource;
    private YamlConfiguration yaml;

    public MessageConfig(Path path, GetDefaultResource getDefaultResource) {
        this.path = path;
        this.getDefaultResource = getDefaultResource;
        this.load();
    }

    public void load() {
        this.yaml = YamlConfiguration.loadConfiguration(this.path.toFile());
        this.yaml.setDefaults(getDefaultResource.getDefaultResource());
        this.yaml.options().copyDefaults(true);
        this.save();
    }

    public String string(String key) {
        return String.join("\n", this.stringList(key));
    }

    public List<String> stringList(String key) {

        if (!this.yaml.isSet(key)) {
            this.yaml.set(key, key + "(could be a list message)");
            this.save();
        }

        Object o = this.yaml.get(key);
        if (o instanceof String) {
            return Lists.newArrayList((( String ) o).split("\n"));
        } else if (o instanceof List) {
            return ( List<String> ) o;
        } else {
            return Lists.newArrayList(key);
        }
    }


    private void save() {
        try {
            this.yaml.save(this.path.toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
