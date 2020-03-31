package example;

import lombok.Data;
import lombok.NonNull;

@Data
public class Example {

    @NonNull private Integer id;
    @NonNull private String text;
}
