package vn.alpaca.ecommerce.api.resttemplate;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {

    private int status;

    private String message;

    private Long timeStamp;
}
