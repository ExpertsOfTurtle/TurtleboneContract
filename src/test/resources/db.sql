CREATE TABLE `contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `contract_no` varchar(20) NOT NULL COMMENT '协议号',
  `contract_name` varchar(50) NOT NULL COMMENT '协议名称',
  `contract_content` text NOT NULL COMMENT '协议内容',
  `contract_type` varchar(20) NOT NULL COMMENT '协议类型',
  `effective_date` datetime NOT NULL COMMENT '有效起期',
  `expired_date` datetime NOT NULL COMMENT '有效止期',
  `contract_status` smallint(6) NOT NULL COMMENT '协议状态',
  `contract_party` varchar(200) NOT NULL COMMENT '签约方',
  `signed_time` varchar(200) DEFAULT NULL COMMENT '签约时间',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `contract_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contactId` int(11) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `action` int(11) DEFAULT NULL COMMENT '0-拒绝，1-接受',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

