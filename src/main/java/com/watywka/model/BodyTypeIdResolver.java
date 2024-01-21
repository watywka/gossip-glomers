package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.google.common.base.CaseFormat;

public class BodyTypeIdResolver extends TypeIdResolverBase {

    private JavaType bt;

    @Override
    public void init(JavaType bt) {
        this.bt = bt;
    }

    @Override
    public String idFromValue(Object value) {
        return idFrom(value.getClass());
    }

    private static String idFrom(Class<?> cl) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, cl.getSimpleName());
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return idFrom(suggestedType);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return null;
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) {
        String className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, id);
        try {
            return context.constructSpecializedType(bt, Class.forName(Body.class.getPackageName() + "." + className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
