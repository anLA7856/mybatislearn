package anla.learn.mybatis.generatorlimit;

import org.mybatis.generator.api.ShellRunner;

/**
 * @author anLA7856
 * @date 19-11-3 下午11:16
 * @description
 */
public class Application {
    public static void main(String[] args) {
        String config = Application.class.getClassLoader()
                .getResource("generatorConfig.xml").getFile();
        String[] arg = { "-configfile", config, "-overwrite" };
        ShellRunner.main(arg);
    }
}
