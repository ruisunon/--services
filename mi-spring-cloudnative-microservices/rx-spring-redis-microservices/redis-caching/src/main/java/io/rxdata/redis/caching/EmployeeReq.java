package io.rxdata.redis.caching;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeReq implements Serializable {
   private static final long serialVersionUID =  6853515607979098228L;
   @JsonProperty("employees")
   private List<Long> id;

}
