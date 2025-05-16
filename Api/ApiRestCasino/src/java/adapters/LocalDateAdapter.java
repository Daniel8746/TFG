/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author danie
 */

// Solo utilizar cuando en la base de datos tiene Date y no se puede cambiar, utilizar con @JsonAdapter
@Deprecated
public class LocalDateAdapter extends TypeAdapter<LocalDate>{

    @Override
    public void write(JsonWriter writer, LocalDate t) throws IOException {
        writer.value(t.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Override
    public LocalDate read(JsonReader reader) throws IOException {
        return LocalDate.parse(reader.nextString(), DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
}
