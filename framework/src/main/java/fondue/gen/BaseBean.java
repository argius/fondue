package fondue.gen;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public abstract class BaseBean {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
