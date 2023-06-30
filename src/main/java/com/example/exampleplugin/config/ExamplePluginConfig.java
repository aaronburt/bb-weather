package com.example.exampleplugin.config;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.bdavies.babblebot.api.plugins.PluginConfig;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@PluginConfig
@Slf4j
@Data
@Builder
public class ExamplePluginConfig {
    private final String someValue;
}
