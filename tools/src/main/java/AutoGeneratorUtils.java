import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Getter;

/**
 * @author: David
 * @date: 27/02/2020
 * @description:
 */
public class AutoGeneratorUtils {
    private static final DB_CONFIG config = DB_CONFIG.TEST;

    /**
     * 代码生成器
     *
     * @param args args
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        mpg.setGlobalConfig(getGlobalConfig());
        mpg.setDataSource(getDataSourceConfig());
        mpg.setPackageInfo(getPackageConfig());
        mpg.setStrategy(getStrategyConfig());
        mpg.setTemplate(getTemplateConfig());

        mpg.execute();
    }

    /**
     * 设置全局配置
     *
     * @author: David
     * @date: 28/02/2020
     */
    private static GlobalConfig getGlobalConfig() {
        // 配置 GlobalConfig
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");

        // 设置输出目录
        globalConfig.setOutputDir(projectPath + config.getPath());
        // 是否覆盖已有文件 [false]
        globalConfig.setFileOverride(true);
        // 是否打开输出目录 [true]
        globalConfig.setOpen(false);
        // 是否在xml中添加二级缓存配置 [true]
        globalConfig.setEnableCache(false);
        // 设置作者(在生成的注解中显示) [默认为空]
        globalConfig.setAuthor("David");
        // 开启kotlin模式 [false]
        globalConfig.setKotlin(false);
        // 开启ActiveRecord模式 [false]
        globalConfig.setActiveRecord(true);
        // 开启BaseResultMap [false]
        globalConfig.setBaseResultMap(true);
        // 开启baseColumnList [false]
        globalConfig.setBaseColumnList(true);
        // mapper命名格式
        globalConfig.setMapperName("%sMapper");
        // xml命名格式
        globalConfig.setXmlName("%sMapper");
        // service命名格式
        globalConfig.setServiceName("%sService");
        // serviceImpl命名格式
        globalConfig.setServiceImplName("%sServiceImpl");
        // controller命名格式
        globalConfig.setControllerName("%sController");
        // 主键ID类型
        //globalConfig.setIdType(IdType.AUTO);
        // 时间类型
        // globalConfig.setDateType(DateType.ONLY_DATE);
        return globalConfig;
    }

    /**
     * 设置数据源
     *
     * @author: David
     * @date: 28/02/2020
     */
    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        // 数据库类型
        dataSourceConfig.setDbType(DbType.MARIADB);
        // schemaname [public]
        dataSourceConfig.setSchemaName("public");
        // 设置数据库字段类型转换类
        dataSourceConfig.setTypeConvert(
                new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        String t = fieldType.toLowerCase();
                        if (t.contains("tinyint(1)")) {
                            return DbColumnType.INTEGER;
                        }
                        if (t.contains("date")) {
                            return DbColumnType.DATE;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                }
        );
        // 驱动连接的URL
        dataSourceConfig.setUrl(config.getDbUrl());
        // 驱动名称
        dataSourceConfig.setDriverName("org.mariadb.jdbc.Driver");
        // 数据库连接用户名
        dataSourceConfig.setUsername(config.getDbUser());
        // 密码
        dataSourceConfig.setPassword(config.getDbPassword());

        return dataSourceConfig;
    }

    /**
     * 包相关的配置项
     *
     * @author: David
     * @date: 28/02/2020
     */
    private static PackageConfig getPackageConfig() {
        PackageConfig packageConfig = new PackageConfig();

        // 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        packageConfig.setParent(config.getPackageParent());
        // 父包模块名
        // packageConfig.setModuleName(scanner("模块名"));
        packageConfig.setModuleName("generator");
        // Entity包名
        packageConfig.setEntity("po");
        // Service包名
        packageConfig.setService("service");
        // Service Impl包名
        packageConfig.setServiceImpl("service.impl");
        // mapper包名
        packageConfig.setMapper("mapper");
        // mapper xml包名
        //packageConfig.setXml("mapper.xml");
        // controller包名
        //packageConfig.setController("controller");
        return packageConfig;
    }

    /**
     * 策略配置项
     *
     * @author: David
     * @date: 28/02/2020
     */
    private static StrategyConfig getStrategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig();
        // 是否大写命名
        strategyConfig.setCapitalMode(false);
        // 数据库表映射到实体的命名策略 [NamingStrategy.nochange]
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略,未指定按照 naming 执行
        // strategyConfig.setColumnNaming(NamingStrategy.nochange);
        // 表前缀
        //strategyConfig.setTablePrefix(new String[] {});
        strategyConfig.setTablePrefix("win_");
        // 字段前缀
        //strategyConfig.setFieldPrefix(new String[] {});
        // 自定义继承的Entity类全称，带包名
        // strategyConfig.setSuperEntityClass();
        // 自定义基础的Entity类，公共字段
        // strategyConfig.setSuperEntityColumns();
        // 自定义继承的Mapper类全称，带包名
        strategyConfig.setSuperMapperClass(ConstVal.SUPER_MAPPER_CLASS);
        // 自定义继承的Service类全称，带包名
        strategyConfig.setSuperServiceClass(ConstVal.SUPER_SERVICE_CLASS);
        // 自定义继承的ServiceImpl类全称，带包名
        strategyConfig.setSuperServiceImplClass(ConstVal.SUPER_SERVICE_IMPL_CLASS);
        // 自定义继承的Controller类全称，带包名
        // strategyConfig.setSuperControllerClass();
        // 需要包含的表名（与exclude二选一配置） [null]
        strategyConfig.setInclude("win_lottery_betslips");
        // 需要排除的表名 [null
        // strategyConfig.setExclude();
        // strategyConfig.setInclude("datasource_config");
        // 是否生成字段常量（默认 false）
        strategyConfig.setEntityColumnConstant(false);
        // 是否为构建者模式 (默认false) 构建者模式:set方法返回this
        //strategyConfig.setEntityBuilderModel(false);
        // 是否为lombok模型(默认false0
        //strategyConfig.setEntityLombokModel(true);
        // Boolean类型字段是否移除is前缀（默认 false）
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(false);
        // 设置Controller为RestController [false]
        //strategyConfig.setRestControllerStyle(true);
        // mapping中驼峰转连字符 [false]
        //strategyConfig.setControllerMappingHyphenStyle(false);
        // 是否生成实体时，生成字段注解
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        // 乐观锁属性名称
        //strategyConfig.setVersionFieldName("updated_at");
        // 逻辑删除属性名称
        // strategyConfig.setLogicDeleteFieldName();
        // 表填充字段 [null]
        strategyConfig.setTableFillList(null);

        return strategyConfig;
    }

    /**
     * 模版配置
     *
     * @author: David
     * @date: 21/03/2020
     */
    private static TemplateConfig getTemplateConfig() {
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        //控制 不生成 controller
        templateConfig.setController("");

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        //templateConfig.setXml(null);
        return templateConfig;
    }

    @Getter
    public enum DB_CONFIG {
        /**
         * Netty 数据库配置
         */
//        DEV("root", "Admin123@", "jdbc:mysql://18.166.2.57:3306/1xWin?tinyInt1isBit=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&", "com.lt.win.dao",
//                "/dao/src/main/java"),
//        TEST("Admin", "Admin123@", "jdbc:mysql://18.163.109.183:3306/1xWin?tinyInt1isBit=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&", "com.lt.win.dao",
//                "/dao/src/main/java"),
        TEST("root", "123456test", "jdbc:mysql://35.220.247.103:3306/lottery10?tinyInt1isBit=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&", "com.lt.win.dao",
                "/dao/src/main/java")

//        NETTY("root", "123456", "jdbc:mysql://127.0.0.1:3306/1xWin?tinyInt1isBit=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&", "com.lt.win.dao", "/dao/src/main/java"),
        ;
        private final String dbUser;
        private final String dbPassword;
        private final String dbUrl;
        private final String packageParent;
        private final String path;

        DB_CONFIG(String dbUser, String dbPassword, String dbUrl, String packageParent, String path) {
            this.dbUser = dbUser;
            this.dbPassword = dbPassword;
            this.dbUrl = dbUrl;
            this.packageParent = packageParent;
            this.path = path;
        }
    }
}

