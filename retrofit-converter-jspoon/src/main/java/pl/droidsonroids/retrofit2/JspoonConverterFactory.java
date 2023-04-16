package pl.droidsonroids.retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jdk.internal.org.jline.utils.Log;
import okhttp3.ResponseBody;
import pl.droidsonroids.jspoon.Jspoon;
import pl.droidsonroids.jspoon.exception.EmptySelectorException;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class JspoonConverterFactory extends Converter.Factory {
    private Jspoon jspoon;

    public static JspoonConverterFactory create() {
        return new JspoonConverterFactory(Jspoon.create());
    }

    public static JspoonConverterFactory create(Jspoon jspoon) {
        return new JspoonConverterFactory(jspoon);
    }

    private JspoonConverterFactory(Jspoon jspoon) {
        this.jspoon = jspoon;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        try {
            return new JspoonResponseBodyConverter<>(retrofit.baseUrl(), jspoon.adapter((Class<?>) type));
        } catch (EmptySelectorException ex) {
            ex.printStackTrace();
            return null; // Let retrofit choose another converter
        }
    }
}
