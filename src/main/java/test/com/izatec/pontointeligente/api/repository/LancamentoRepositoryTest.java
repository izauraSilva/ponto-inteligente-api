package test.com.izatec.pontointeligente.api.repository;

import com.izatec.pontointeligente.api.entities.Empresa;
import com.izatec.pontointeligente.api.entities.Funcionario;
import com.izatec.pontointeligente.api.entities.Lancamento;
import com.izatec.pontointeligente.api.enums.PerfilEnum;
import com.izatec.pontointeligente.api.enums.TipoEnum;
import com.izatec.pontointeligente.api.repository.EmpresaRepository;
import com.izatec.pontointeligente.api.repository.FuncionarioRepository;
import com.izatec.pontointeligente.api.repository.LancamentoRepository;
import com.izatec.pontointeligente.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private Long funcionarioId;

    @Before
    public void setUp() throws Exception {
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());

        Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
        this.funcionarioId = funcionario.getId();

        this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
        this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
    }

    @After
    public void tearDown() throws Exception {
        this.empresaRepository.deleteAll();
    }

    @Test
    @Ignore
    public void testBuscarLancamentoPorFuncionarioId(){
        List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
        assertEquals(2, lancamentos.size());
    }

    @Test
    @Ignore
    public void testBuscarLancamentoPorFuncionarioIdPaginado(){
        PageRequest  page = new PageRequest(0 , 10);
        Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);
        assertEquals(2, lancamentos.getTotalElements());
    }

    private Lancamento obterDadosLancamentos(Funcionario funcionario){
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao("Lancamento Teste");
        lancamento.setLocalizacao("Localização Teste");
        lancamento.setData(new Date());
        lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
        lancamento.setFuncionario(funcionario);
        return lancamento;
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Fulano de Tal");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
        funcionario.setCpf("25888969877");
        funcionario.setEmail("email@gmail.com");
        funcionario.setQtHorasTrabalhoDia(new Float(1));
        funcionario.setValorHora(new BigDecimal(55));
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

    private Empresa obterDadosEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("empresa de exemplo");
        empresa.setCnpj("51463645000100");
        return empresa;
    }

}
