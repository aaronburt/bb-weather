package uk.co.aaronburt;

import uk.co.aaronburt.model.TestRepository;
import uk.co.aaronburt.model.WeatherJsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bdavies.babblebot.api.command.Command;
import net.bdavies.babblebot.api.command.CommandParam;
import net.bdavies.babblebot.api.command.ICommandContext;
import net.bdavies.babblebot.api.obj.message.discord.DiscordMessage;
import net.bdavies.babblebot.api.obj.message.discord.embed.EmbedAuthor;
import net.bdavies.babblebot.api.obj.message.discord.embed.EmbedField;
import net.bdavies.babblebot.api.obj.message.discord.embed.EmbedMessage;
import net.bdavies.babblebot.api.plugins.Plugin;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Edit me
 *
 * @author me@bdavies (Ben Davies)
 * @since 1.0.0
 */
@Plugin
@RequiredArgsConstructor
@Slf4j
public class Weather {
    private final TestRepository repository;

    @Command(aliases = "get", description = "get the weather from zipcode")
    @CommandParam(value = "zip", canBeEmpty = false, optional = false, exampleValue = "st7")
    @CommandParam(value = "country", canBeEmpty = false, optional = false, exampleValue = "gb")
    public EmbedMessage getWeather(DiscordMessage message, ICommandContext context) {
        try {
            String weatherUrlFormat = "https://weather.function.aaronburt.co.uk/current/%s/%s";
            String weatherUrlFormatted = String.format(weatherUrlFormat, context.getParameter("zip"), context.getParameter("country"));

            ObjectMapper mapper = new ObjectMapper();
            HttpURLConnection url = (HttpURLConnection) new URL(weatherUrlFormatted).openConnection();
            WeatherJsonFormat jsonResponse = mapper.readValue(url.getInputStream(), WeatherJsonFormat.class);
            url.disconnect();

            String titleFormat = "Weather in %s";
            String title = String.format(titleFormat, jsonResponse.getCity());

            String imageFormat = "https://weather.icon.static.aaronburt.co.uk/%s@2x.png";
            String image = String.format(imageFormat, jsonResponse.getWeather().get(0).getIcon());

            return EmbedMessage
                    .builder()
                    .title(title)
                    .description(jsonResponse.getWeather().get(0).getDescription() + ", at " +  (int) Math.ceil(jsonResponse.getMain().getFeelsLike()) + "\u2103")
                    .author(EmbedAuthor.builder().name(" ").build())
                    .image(image)
                    .addField(
                        EmbedField
                            .builder()
                            .inline(true)
                            .name("High")
                            .value(
                                    (int) Math.ceil(jsonResponse.getMain().getTempMax()) + "\u2103"
                            ).build()
                    )

                    .addField(
                        EmbedField
                            .builder()
                            .inline(true)
                            .name("Low")
                            .value(
                                String.valueOf(
                                    (int)Math.floor(jsonResponse.getMain().getTempMin()) + "\u2103"
                                )
                            ).build())



                    .addField(EmbedField.builder().inline(true).name("Humidity").value(jsonResponse.getMain().getHumidity() + "%").build())
                    .build();


        } catch (Exception e) {
            return EmbedMessage.builder().title("Error :(").build();
        }
    }
}



