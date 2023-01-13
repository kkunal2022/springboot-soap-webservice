package com.kunal.springboot.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kunal
 * @project SpringBoot-SOAP-WS-main
 */
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Customer {

    private int id;
    private String customerCR;
    private String password;

}
