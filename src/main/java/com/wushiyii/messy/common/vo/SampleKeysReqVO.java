package com.wushiyii.messy.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SampleKeysReqVO extends PageVO {

    List<String> keys;

}
