package live.smoothing.ruleengine.node;

import com.google.gson.JsonPrimitive;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import live.smoothing.ruleengine.sensor.dto.SensorMessage;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class InfluxDbInsertNode extends Node {

    private static final String VALUE_KEY = "value";
    private static final String MEASUREMENT_KEY = "event";
    private static final String TIME_KEY = "time";

    private final InfluxDBClient influxDBClient;

    @Builder
    public InfluxDbInsertNode(String nodeId,
                              int outputPortCount,
                              String serverURL,
                              String token,
                              String org,
                              String bucket) {

        super(nodeId, outputPortCount);
        this.influxDBClient = InfluxDBClientFactory.create(serverURL, token.toCharArray(), org, bucket);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                SensorMessage sensorMessage = tryGetMessage();
                log.info("influx node message = {}", sensorMessage);

                WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

                StringBuilder builder = new StringBuilder();
                builder.append(sensorMessage.getAttribute(MEASUREMENT_KEY));
                for (String key : sensorMessage.getKeys()) {
                    if (key.equals(TIME_KEY) || key.equals(VALUE_KEY) || key.equals(MEASUREMENT_KEY)) {
                        continue;
                    }

                    builder.append(",").append(key).append("=").append(sensorMessage.getAttribute(key));
                }

                builder.append(" ").append(VALUE_KEY);
                if(sensorMessage.getAttribute(VALUE_KEY) instanceof JsonPrimitive){
                    if (((JsonPrimitive)sensorMessage.getAttribute(VALUE_KEY)).isString()) {
                        builder.append("=\"").append(sensorMessage.getAttribute(VALUE_KEY)).append("\"");
                    } else {
                        builder.append("=").append(sensorMessage.getAttribute(VALUE_KEY));
                    }
                } else {
                    builder.append("=").append(0);
                }

                Instant instant = Instant.ofEpochMilli(Long.parseLong(sensorMessage.getAttribute(TIME_KEY).toString()));
                ZonedDateTime seoulDateTime = instant.atZone(ZoneId.of("Asia/Seoul"));
                builder.append(" ").append(seoulDateTime.toInstant().toEpochMilli());

                writeApi.writeRecord(WritePrecision.MS, builder.toString());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }catch (NullPointerException e){
                log.error(e.getMessage());
            }
        }
    }
}