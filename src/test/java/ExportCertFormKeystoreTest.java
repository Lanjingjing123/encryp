/**
 * @author lanjingjing
 * @description ExportCertFormKeystoreTest
 * @date 2020/5/31
 */
import com.csii.ljj.ExportCertFormKeystore;
import org.junit.Test;

public class ExportCertFormKeystoreTest {

    @Test
    public void genkeyTest() {
        //生成密钥测试
        new ExportCertFormKeystore().genkey();
    }
    @Test
    public void exportTest() {
        //导出证书文件测试
        new ExportCertFormKeystore().export();
    }

}