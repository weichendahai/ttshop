-- ----------------------------
-- Table structure for shop_banner
-- ----------------------------
DROP TABLE IF EXISTS `shop_banner`;
CREATE TABLE `shop_banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT '商品id',
  `sort_factor` int(11) DEFAULT NULL COMMENT '排序因子',
  `poster_image_addr` varchar(100) DEFAULT NULL COMMENT '海报地址',
  `state` int(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='设置首页banner表';

-- ----------------------------
-- Table structure for shop_bonus_payment
-- ----------------------------
DROP TABLE IF EXISTS `shop_bonus_payment`;
CREATE TABLE `shop_bonus_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `total` int(11) DEFAULT NULL COMMENT '提现金额，单位分',
  `create_date` datetime DEFAULT NULL COMMENT '申请时间',
  `state` int(11) DEFAULT NULL COMMENT '当前状态：0申请中，1处理中，2已支付',
  `payment_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='佣金提现表';

-- ----------------------------
-- Table structure for shop_brand
-- ----------------------------
DROP TABLE IF EXISTS `shop_brand`;
CREATE TABLE `shop_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manufactor_id` int(11) DEFAULT NULL COMMENT '厂家id',
  `brand_name` varchar(100) DEFAULT NULL COMMENT '品牌名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='品牌表';

-- ----------------------------
-- Table structure for shop_coupon
-- ----------------------------
DROP TABLE IF EXISTS `shop_coupon`;
CREATE TABLE `shop_coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '团购券',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='优惠券表';

-- ----------------------------
-- Table structure for shop_coupon_condition
-- ----------------------------
DROP TABLE IF EXISTS `shop_coupon_condition`;
CREATE TABLE `shop_coupon_condition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `condition_type` int(11) DEFAULT NULL COMMENT '类型，0限定金额； 1限定商品，2限定品牌，3限定地区',
  `condition_desc` varchar(50) DEFAULT NULL COMMENT '条件的描述，例如满足999-50',
  `parameter_first` int(11) DEFAULT NULL COMMENT '第一个条件参数，例如满足999',
  `parameter_second` int(11) DEFAULT NULL COMMENT '第二个参数，表示满足第一个条件后，带来的结果，例如减50',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='优惠券使用条件表';

-- ----------------------------
-- Table structure for shop_evaluate_bonus
-- ----------------------------
DROP TABLE IF EXISTS `shop_evaluate_bonus`;
CREATE TABLE `shop_evaluate_bonus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `evaluate_id` bigint(20) DEFAULT NULL COMMENT '点评id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id,冗余信息',
  `item_id` int(11) DEFAULT NULL COMMENT '商品id,冗余信息',
  `create_date` datetime DEFAULT NULL COMMENT '产生时间',
  `bonus` int(11) DEFAULT NULL COMMENT '奖金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='点评佣金表';

-- ----------------------------
-- Table structure for shop_evaluate_shared
-- ----------------------------
DROP TABLE IF EXISTS `shop_evaluate_shared`;
CREATE TABLE `shop_evaluate_shared` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `evaluate_id` bigint(20) DEFAULT NULL COMMENT '点评id，shop_item_evaluate表',
  `user_id` bigint(20) DEFAULT NULL COMMENT '分享的用户id',
  `create_date` datetime DEFAULT NULL COMMENT '分享时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8 COMMENT='点评的分享记录表';

-- ----------------------------
-- Table structure for shop_feedback
-- ----------------------------
DROP TABLE IF EXISTS `shop_feedback`;
CREATE TABLE `shop_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `fid` int(11) DEFAULT NULL COMMENT '反馈id，如果为0，则表示为此此反馈的第一条,每条反馈有相同的fid,按照时间顺序来区分回复时间',
  `from` int(11) DEFAULT NULL COMMENT '如果是用户则保存用户user_id，如果是商城客服人员保存为-1',
  `content` varchar(400) DEFAULT NULL COMMENT '回复内容',
  `img_addr` varchar(100) DEFAULT NULL COMMENT '图片地址',
  `read_date` datetime DEFAULT NULL COMMENT '阅读的时间,如果为空,表示还没阅读,是未读状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_gift_card
-- ----------------------------
DROP TABLE IF EXISTS `shop_gift_card`;
CREATE TABLE `shop_gift_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `balance` int(11) DEFAULT NULL COMMENT '红包金额',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(100) DEFAULT NULL COMMENT '红包名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='红包表';

-- ----------------------------
-- Table structure for shop_hot_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_hot_item`;
CREATE TABLE `shop_hot_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT '商品id',
  `item_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `item_icon_addr` varchar(100) DEFAULT NULL COMMENT '商品图标地址',
  `evaluate_count` int(11) DEFAULT NULL COMMENT '点评数量',
  `grade` int(11) DEFAULT NULL COMMENT '评分',
  `price` int(11) DEFAULT NULL COMMENT '价格',
  `advantage` varchar(200) DEFAULT NULL COMMENT '点评优点',
  `sort_factor` int(11) DEFAULT NULL COMMENT '排序因子',
  `session_no` int(11) DEFAULT NULL COMMENT '阶段，期数，爆款商品列表，每隔一段时间会更新一次，此数据就是维护爆款列表时，自动增加的',
  `amount` int(11) DEFAULT NULL COMMENT '销售的数量,根据shop_order_item表获取,属于冗余数据',
  `create_date` datetime DEFAULT NULL COMMENT '生成列表的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='爆款商品，大多数都是商品的冗余字段，应该存放在Redis中';

-- ----------------------------
-- Table structure for shop_hot_search_keyword
-- ----------------------------
DROP TABLE IF EXISTS `shop_hot_search_keyword`;
CREATE TABLE `shop_hot_search_keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `search_keyword` varchar(45) DEFAULT NULL COMMENT '热词',
  `sort_factor` varchar(45) DEFAULT NULL COMMENT '热词排序因子，数值越小越靠前',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='热搜榜';

-- ----------------------------
-- Table structure for shop_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_item`;
CREATE TABLE `shop_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `item_icon_addr` varchar(100) DEFAULT NULL COMMENT '商品图标',
  `info_image_addrs` varchar(500) DEFAULT NULL COMMENT '商品详情使用图片',
  `info_big_image_addr` varchar(100) DEFAULT NULL COMMENT '商品详细页面使用的上方大图',
  `item_effect` varchar(200) DEFAULT NULL COMMENT '商品效果',
  `item_compose` varchar(100) DEFAULT NULL COMMENT '商品成分',
  `manufactor_id` int(11) DEFAULT NULL COMMENT '厂商id',
  `price` int(11) DEFAULT NULL COMMENT '售价',
  `item_state` int(11) DEFAULT NULL COMMENT '当前状态，0创建，1上架，下架-1，-2删除',
  `item_category_primary` int(11) DEFAULT NULL COMMENT '一级分类',
  `item_category_sub` int(11) DEFAULT NULL COMMENT '二级分类',
  `brand_id` int(11) DEFAULT NULL COMMENT '品牌id',
  `create_date` datetime DEFAULT NULL,
  `sort_factor` int(11) DEFAULT NULL COMMENT '排序种子，数值约小越靠前',
  `evaluate_bonus_rate` int(11) DEFAULT NULL COMMENT '点评佣金比例，如果字段evaluate_bonus_value不为空，则此字段无效',
  `evaluate_bonus_value` int(11) DEFAULT NULL COMMENT '点评佣金金额，如果此字段不为空，则点评佣金比例无效',
  `shared_bonus_rate` int(11) DEFAULT NULL COMMENT '分享佣金比例，如果shared_bonus_value字段有值，则此字段无效',
  `shared_bonus_value` int(11) DEFAULT NULL COMMENT '分享佣金金额，如果此字段有值，则分享佣金比例字段无效',
  `discount_rate` int(11) DEFAULT NULL COMMENT '分享购买的折扣率,例如90就是打九折,如果discount_value有值，则此字段无效',
  `discount_value` int(11) DEFAULT NULL COMMENT '分享购买的折扣价格,例如4500,如果此字段有值，则discount_rate无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8 COMMENT='商城商品表';

-- ----------------------------
-- Table structure for shop_item_category
-- ----------------------------
DROP TABLE IF EXISTS `shop_item_category`;
CREATE TABLE `shop_item_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_level` int(11) DEFAULT NULL COMMENT '分类的级别',
  `category_name` varchar(40) DEFAULT NULL COMMENT '分类的名称',
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='商品分类表，配合商品使用';

-- ----------------------------
-- Table structure for shop_item_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `shop_item_evaluate`;
CREATE TABLE `shop_item_evaluate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `item_id` int(11) DEFAULT NULL COMMENT '点评商品id',
  `advantage` varchar(200) DEFAULT NULL COMMENT '优点',
  `disadvantage` varchar(200) DEFAULT NULL COMMENT '缺点',
  `evaluate_image_addr` varchar(600) DEFAULT NULL COMMENT '点评的图片地址',
  `evaluate_vedio_addr` varchar(100) DEFAULT NULL COMMENT '点评的视频地址',
  `other_item_ids` varchar(100) DEFAULT NULL COMMENT '第一个相关产品id',
  `grade` int(11) DEFAULT NULL COMMENT '评分，按照100分屏，最后的结果除以10，避免小数',
  `create_date` datetime DEFAULT NULL COMMENT '点评创建时间',
  `evaluate_state` int(11) DEFAULT NULL COMMENT '点评的状态：0正常，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8 COMMENT='用户点评表，记录点评信息';

-- ----------------------------
-- Table structure for shop_item_price
-- ----------------------------
DROP TABLE IF EXISTS `shop_item_price`;
CREATE TABLE `shop_item_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT '商品id',
  `property_path` varchar(200) DEFAULT NULL COMMENT '规格选取路径,根据选取的路径,得到价格，路径的样式是0:0/1:2/4:0',
  `price` int(11) DEFAULT NULL COMMENT '价格，单位分',
  `property_path_name` varchar(200) DEFAULT NULL COMMENT '显示使用的规格文字描述，与property_path对应，其的样式是红色/60ml/礼品包装',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=309 DEFAULT CHARSET=utf8 COMMENT='商品价格表，商品规格关联的价格';

-- ----------------------------
-- Table structure for shop_item_properties
-- ----------------------------
DROP TABLE IF EXISTS `shop_item_properties`;
CREATE TABLE `shop_item_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT '商品id',
  `property_key` int(11) DEFAULT NULL COMMENT '属性的key，不同的值代表色号，型号，容量，款型等',
  `property_value` int(11) DEFAULT NULL COMMENT '针对属性key的数值，例如红色，白色，蓝色等颜色，也可以是300ml，500ml等',
  `property_desc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=605 DEFAULT CHARSET=utf8 COMMENT='商品属性表，型号，颜色，容量等';

-- ----------------------------
-- Table structure for shop_item_property_name
-- ----------------------------
DROP TABLE IF EXISTS `shop_item_property_name`;
CREATE TABLE `shop_item_property_name` (
  `property_key` int(11) NOT NULL AUTO_INCREMENT COMMENT '规格key',
  `property_name` varchar(45) DEFAULT NULL COMMENT '规格名称',
  PRIMARY KEY (`property_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='商品规格维护表，此表用于维护规格，将规格';

-- ----------------------------
-- Table structure for shop_item_user_distribution
-- ----------------------------
DROP TABLE IF EXISTS `shop_item_user_distribution`;
CREATE TABLE `shop_item_user_distribution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT '商品id',
  `type` int(11) DEFAULT NULL COMMENT '分布类型',
  `sub` int(11) DEFAULT NULL COMMENT '分布key,如果type是年龄分布,�',
  `count` int(11) DEFAULT NULL COMMENT '分布数量',
  `distribution_type_des` varchar(100) DEFAULT NULL,
  `distribution_sub_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='商品用户分布表';

-- ----------------------------
-- Table structure for shop_manufactor
-- ----------------------------
DROP TABLE IF EXISTS `shop_manufactor`;
CREATE TABLE `shop_manufactor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manufactor` varchar(100) DEFAULT NULL COMMENT '厂商名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='厂商表';

-- ----------------------------
-- Table structure for shop_option
-- ----------------------------
DROP TABLE IF EXISTS `shop_option`;
CREATE TABLE `shop_option` (
  `option_key` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数key',
  `option_value` varchar(50) DEFAULT NULL COMMENT '参数数值',
  `option_desc` varchar(50) DEFAULT NULL COMMENT '参数描述信息',
  PRIMARY KEY (`option_key`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='商城参数';

-- ----------------------------
-- Table structure for shop_order
-- ----------------------------
DROP TABLE IF EXISTS `shop_order`;
CREATE TABLE `shop_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_code` varchar(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_state` tinyint(4) DEFAULT NULL COMMENT '    0  代付款    1  代发货   2  待收货   3带评价 4已完成 ',
  `express_code` varchar(50) DEFAULT NULL,
  `order_total` int(11) DEFAULT NULL COMMENT '订单金额，单位分，注意100是一元钱，均是整数，显示时，除以100显示。',
  `order_addr_id` int(11) DEFAULT NULL COMMENT '订单的收货地址id，关联用户地址表的id',
  `freight` int(11) DEFAULT NULL COMMENT '运费，单位分，100是一元',
  `user_coupon_id` int(11) DEFAULT NULL COMMENT '使用的优惠券id，shop_user_promo表的id',
  `user_gift_card_id` int(11) DEFAULT NULL COMMENT '使用的红包id，shop_user_promo表的id',
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `trans_number` varchar(50) DEFAULT NULL COMMENT '交易单号，微信交易流水号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=646546435143247 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for shop_order_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_item`;
CREATE TABLE `shop_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `item_id` int(11) DEFAULT NULL COMMENT '商品id',
  `price` int(11) DEFAULT NULL COMMENT '单价',
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `state` int(11) DEFAULT NULL COMMENT '状态，0表示正常，1表示已经退货',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8 COMMENT='订单商品关系表';

-- ----------------------------
-- Table structure for shop_order_item_properties
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_item_properties`;
CREATE TABLE `shop_order_item_properties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_item_id` bigint(20) DEFAULT NULL COMMENT 'shop_order_item_properties表的id，用于区分订单中的商品',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `item_id` int(11) DEFAULT NULL COMMENT '商品id',
  `property_key` int(11) DEFAULT NULL COMMENT '属性的key，不同的值代表色号，型号，容量，款型等',
  `property_value` int(11) DEFAULT NULL COMMENT '针对属性key的数值，例如红色，白色，蓝色等颜色，也可以是300ml，500ml等',
  `price` int(11) DEFAULT NULL COMMENT '单价',
  `count` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=320 DEFAULT CHARSET=utf8 COMMENT='订单商品关系表';

-- ----------------------------
-- Table structure for shop_sample
-- ----------------------------
DROP TABLE IF EXISTS `shop_sample`;
CREATE TABLE `shop_sample` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT '商品id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `start_date` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  `apply_count` int(11) DEFAULT NULL COMMENT '申请数量',
  `item_count` int(11) DEFAULT NULL COMMENT '试用数量',
  `sample_image_addr` varchar(100) DEFAULT NULL COMMENT '试用图片地址',
  `state` int(11) DEFAULT '0' COMMENT '状态，0未开始，1，申请中，2，已结束，3已分配',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='试用表';

-- ----------------------------
-- Table structure for shop_search_history
-- ----------------------------
DROP TABLE IF EXISTS `shop_search_history`;
CREATE TABLE `shop_search_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户表',
  `search_keyword` varchar(50) DEFAULT NULL COMMENT '搜索历史关键词',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=512 DEFAULT CHARSET=utf8 COMMENT='历史搜索记录表';

-- ----------------------------
-- Table structure for shop_shared_bonus
-- ----------------------------
DROP TABLE IF EXISTS `shop_shared_bonus`;
CREATE TABLE `shop_shared_bonus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shared_id` bigint(20) DEFAULT NULL COMMENT '分享id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `item_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `create_date` datetime DEFAULT NULL COMMENT '产生时间',
  `bonus` int(11) DEFAULT NULL COMMENT '佣金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户分享奖励表';

-- ----------------------------
-- Table structure for shop_user
-- ----------------------------
DROP TABLE IF EXISTS `shop_user`;
CREATE TABLE `shop_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(100) DEFAULT NULL,
  `head_image_addr` varchar(100) DEFAULT NULL COMMENT '头像地址',
  `sex` int(11) DEFAULT NULL COMMENT '性别，0，男，1，女',
  `mobile_phone` varchar(14) DEFAULT NULL,
  `date_of_birth` datetime DEFAULT NULL,
  `skin_type` int(11) DEFAULT NULL COMMENT '肤质：0干性，1混合，2油性，3中性，4敏感',
  `email` varchar(100) DEFAULT NULL,
  `default_addr` int(11) DEFAULT NULL COMMENT '默认地址',
  `shard_count` int(11) DEFAULT NULL COMMENT '分享数量',
  `evaluate_count` int(11) DEFAULT NULL COMMENT '点评数量',
  `shard_bonus` int(11) DEFAULT NULL COMMENT '分享佣金',
  `evaluate_bonus` int(11) DEFAULT NULL COMMENT '点评佣金',
  `open_id` varchar(50) DEFAULT NULL,
  `union_id` varchar(50) DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '用户状态:0是正常,1不能购买,2不能点评,3没有佣金,4,不能提现',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2106 DEFAULT CHARSET=utf8 COMMENT='商城用户表';

-- ----------------------------
-- Table structure for shop_user_addr
-- ----------------------------
DROP TABLE IF EXISTS `shop_user_addr`;
CREATE TABLE `shop_user_addr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系人',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `mobile_phone` varchar(14) DEFAULT NULL COMMENT '电话',
  `deleted` int(11) DEFAULT NULL COMMENT '0是正常状态，1是删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COMMENT='用户地址表';

-- ----------------------------
-- Table structure for shop_user_filter_record
-- ----------------------------
DROP TABLE IF EXISTS `shop_user_filter_record`;
CREATE TABLE `shop_user_filter_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scheme_id` int(11) DEFAULT NULL COMMENT 'shop_user_filter_scheme表的id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `create_date` date DEFAULT NULL COMMENT '方案应用时间',
  `reason` int(11) DEFAULT NULL COMMENT '0表示商品试用筛选，1表示优惠券筛选，2表示红包筛选',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1107 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_user_filter_scheme
-- ----------------------------
DROP TABLE IF EXISTS `shop_user_filter_scheme`;
CREATE TABLE `shop_user_filter_scheme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_user_promo
-- ----------------------------
DROP TABLE IF EXISTS `shop_user_promo`;
CREATE TABLE `shop_user_promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `promo_id` int(11) DEFAULT NULL COMMENT '优惠券或者红包id，对应的是优惠券表id，或者红包表id',
  `grant_date` datetime DEFAULT NULL COMMENT '发放时间',
  `promo_type` tinyint(4) DEFAULT NULL COMMENT '0表示优惠券，1表示红包',
  `state` int(11) DEFAULT NULL COMMENT '状态，0未用，1已用',
  `end_date` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1103 DEFAULT CHARSET=utf8 COMMENT='用户拥有的优惠券红包表';

-- ----------------------------
-- Table structure for shop_user_refund
-- ----------------------------
DROP TABLE IF EXISTS `shop_user_refund`;
CREATE TABLE `shop_user_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `item_id` int(11) DEFAULT NULL COMMENT '商品id',
  `create_date` datetime DEFAULT NULL COMMENT '申请时间',
  `reason` int(11) DEFAULT NULL COMMENT '作废掉,使用refund_type',
  `info` varchar(200) DEFAULT NULL COMMENT '具体描述',
  `image_addr` varchar(100) DEFAULT NULL COMMENT '图片地址',
  `refund_state` int(11) DEFAULT NULL COMMENT '退货状态0代表退货中1代表退货完成2退货失败',
  `refund_type` int(11) DEFAULT NULL COMMENT '  退货类型 0：退货退款\r\n 1：退货 \r\n2 ：退款',
  `refund_charge` int(11) DEFAULT NULL COMMENT '退货的金额',
  `order_item_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='用户退款记录表';

-- ----------------------------
-- Table structure for shop_user_refund_feedback
-- ----------------------------
DROP TABLE IF EXISTS `shop_user_refund_feedback`;
CREATE TABLE `shop_user_refund_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_refund_id` int(11) DEFAULT NULL COMMENT 'shop_user_refund表id',
  `create_date` date DEFAULT NULL COMMENT '此消息创建时间',
  `message` varchar(500) DEFAULT NULL COMMENT '反馈信息',
  `from` int(11) DEFAULT NULL COMMENT '0表示客户反馈，1表示客服反馈，2表示库房反馈，3表示商城反馈，4客户经理反馈',
  `image_addr` varchar(100) DEFAULT NULL COMMENT '附带的图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop_user_sample
-- ----------------------------
DROP TABLE IF EXISTS `shop_user_sample`;
CREATE TABLE `shop_user_sample` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sample_id` int(11) DEFAULT NULL COMMENT '试用id,shop_sample表id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `state` tinyint(4) DEFAULT NULL COMMENT '状态，0申请中，1中签，2未中签，3待评价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COMMENT='用户试用订单表，';
