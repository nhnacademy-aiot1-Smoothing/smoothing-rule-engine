package live.smoothing.ruleengine.common;

import lombok.Getter;

import java.util.Map;
import java.util.Set;

public class Parameters {
    private final Map<String, String> params;

    public Parameters(Map<String, String> params) {
        this.params = params;
    }

    public void addParams(Map<String, String> params) {
        this.params.putAll(params);
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public Set<String> getKeys() {
        return params.keySet();
    }
}