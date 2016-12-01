package org.paradise.microservice.userpreference.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by terrence on 28/11/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPreferences {

    @JsonProperty("c_number")
    private String cNumber;

    @JsonProperty("apbcn")
    private String apbcn;

    @JsonProperty("preference_type")
    private PreferenceType preferenceType;

    @JsonProperty("date_time_created")
    private String dateTimeCreated;

    @JsonProperty("date_time_updated")
    private String dateTimeUpdated;

    @Valid
    @NotNull
    @JsonProperty("preferences")
    private Map<String, Object> preferences;


    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public String getApbcn() {
        return apbcn;
    }

    public void setApbcn(String apbcn) {
        this.apbcn = apbcn;
    }

    public PreferenceType getPreferenceType() {
        return preferenceType;
    }

    public void setPreferenceType(PreferenceType preferenceType) {
        this.preferenceType = preferenceType;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getDateTimeUpdated() {
        return dateTimeUpdated;
    }

    public void setDateTimeUpdated(String dateTimeUpdated) {
        this.dateTimeUpdated = dateTimeUpdated;
    }

    public Map<String, Object> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<String, Object> preferences) {
        this.preferences = preferences;
    }

}
