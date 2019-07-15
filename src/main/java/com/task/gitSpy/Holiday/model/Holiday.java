package com.task.gitSpy.Holiday.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "date",
        "observed",
        "public",
        "country"
})
@ToString(callSuper=true, includeFieldNames=true)
public class Holiday {

    @JsonProperty("name")
    @Getter
    @Setter
    private String name;

    @JsonProperty("date")
    @Getter
    @Setter
    private String date;

    @JsonProperty("observed")
    @Getter
    @Setter
    private String observed;

    @JsonProperty("public")
    @Getter
    @Setter
    private Boolean isPublic;
    
    @JsonProperty("country")
    @Getter
    @Setter
    private String country;

}
