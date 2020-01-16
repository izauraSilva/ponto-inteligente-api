package test.com.izatec.pontointeligente.api.utils;

import static org.junit.Assert.assertNull;

import com.izatec.pontointeligente.api.utils.PasswordUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.junit.Assert.assertTrue;

public class PasswordUtilsTest {

    private static final String SENHA = "123456";

    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

    @Test
    @Ignore
    public void testSenhaNull() throws Exception {
        assertNull(PasswordUtils.gerarBCrypt(SENHA));
    }

    @Test
    @Ignore
    public void testGerarHashSenha() throws Exception {
        String hash = PasswordUtils.gerarBCrypt(SENHA);
        assertTrue(bCryptEncoder.matches(SENHA,hash));
    }
}
