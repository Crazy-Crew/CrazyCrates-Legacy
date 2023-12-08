package us.crazycrew.crazycrates.common.config;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import org.jetbrains.annotations.NotNull;
import us.crazycrew.crazycrates.common.config.types.ConfigKeys;
import us.crazycrew.crazycrates.common.config.types.MessageKeys;

import java.io.File;

public class ConfigManager {

    private final File dataFolder;

    public ConfigManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    private SettingsManager config;

    private SettingsManager messages;

    public void load() {
        YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

        this.config = SettingsManagerBuilder
                .withYamlFile(new File(this.dataFolder, "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class)
                .create();

        this.messages = SettingsManagerBuilder
                .withYamlFile(new File(this.dataFolder, "messages.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(MessageKeys.class)
                .create();
    }

    public void reload() {
        this.config.reload();

        this.messages.reload();
    }

    @NotNull
    public SettingsManager getConfig() {
        return config;
    }

    @NotNull
    public SettingsManager getMessages() {
        return this.messages;
    }
}