package edu.zc.globalException.exception;

import lombok.*;

/**
 * @author: keeplooking
 * @since: 2022/01/01 - 14:52
 */
@EqualsAndHashCode(callSuper = false)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DiyException extends RuntimeException {
    private Integer code;
    private String msg;
}
