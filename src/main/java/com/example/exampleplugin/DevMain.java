package com.example.exampleplugin;

import com.example.exampleplugin.config.ExamplePluginConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.bdavies.babblebot.BabblebotApplication;
import net.bdavies.babblebot.api.IApplication;
import net.bdavies.babblebot.api.config.EPluginPermission;
import net.bdavies.babblebot.api.plugins.PluginType;
import net.bdavies.babblebot.plugins.PluginConfigParser;
import net.bdavies.babblebot.plugins.PluginModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Dev Main class for Development Only will not be used inside the
 * main application when importing the plugin
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */

@Slf4j
@SpringBootApplication
@Import(BabblebotApplication.class)
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"net.bdavies.babblebot", "com.example.exampleplugin"})
@EntityScan(basePackages = {"net.bdavies.babblebot", "com.example.exampleplugin"})
public class DevMain {
    public static void main(String[] args) {
        IApplication app = BabblebotApplication.make(DevMain.class, args);
    }

    @Bean
    CommandLineRunner onBoot(GenericApplicationContext gac, IApplication app, PluginConfigParser parser) {
        return args -> {
            gac.registerBean(ExamplePlugin.class);
            ExamplePlugin plugin = app.get(ExamplePlugin.class);
            val configObj = ExamplePluginConfig.builder()
                    .someValue("Test")
                    .build();
            gac.registerBean(ExamplePluginConfig.class, () -> configObj);
            String config = parser.pluginConfigToString(configObj);
            app.getPluginContainer()
                    .addPlugin(
                            plugin,
                            PluginModel
                                    .builder()
                                    .name("example")
                                    .pluginType(PluginType.JAVA)
                                    .config(config)
                                    .namespace("ep")
                                    .pluginPermissions(EPluginPermission.all())
                                    .build()
                    );
        };
    }
}