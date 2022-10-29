package testDeMapstruct;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.biblioteca.entities.Emprestimo;
import com.biblioteca.entities.Livro;
import com.biblioteca.entities.Usuario;
import com.biblioteca.requests.EmprestimosPutRequestBody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Mapper
interface Mapeador {
  Mapeador MAPEADOR = Mappers.getMapper(Mapeador.class);


  void atualize(Entidade ent, @MappingTarget Resposta resposta);
  void atualizeEmprestimo(EmprestimosPutRequestBody emprestimoPutRequestBody,@MappingTarget Emprestimo emprestimoSaved);
}

@AllArgsConstructor
@Getter
@ToString
class Entidade {
  boolean ativo;
  String descricao;
}

@Setter
@ToString
class Resposta {
  boolean ativo = false;
  String descricao = "Nenhuma";
  String versao = "32.9";
  String name = "guizera";
}

//public class MapeamentoParaAtualizacao {
//  public static void main(String[] args) {
//    Resposta re = new Resposta();
//    System.out.println(re);
//    // Resposta(ativo=false, descricao=Nenhuma, versao=null)
//    Mapeador.MAPEADOR.atualize(new Entidade(true, "MapStruct"), re);
//    System.out.println(re);
//    // Resposta(ativo=true, descricao=MapStruct, versao=1.4.2)
//  }
//}

public class MapeamentoParaAtualizacao {
	  public static void main(String[] args) throws ParseException {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		  
		  
	    Emprestimo emprestimoSaved = new Emprestimo(1l, sdf.parse("2022/05/22"), sdf.parse("2022/06/26"));
	    emprestimoSaved.setUsuario(new Usuario(1l, "gui",null, null, null));
	    Livro livro = new Livro(1l, "angular", null);
	    emprestimoSaved.getLivros().add(livro);
	    
	    
	    //no exemplo deu certo agora vamos reproduzir no nas classes de exemplo
	    System.out.println("data devolucao = " + sdf.format(emprestimoSaved.getDataDevolucao()));
	    System.out.println("livro titulo = " + emprestimoSaved.getLivros().contains(livro));
	    System.out.println("data devolucao = " + emprestimoSaved.getUsuario().getName());
	    
	    EmprestimosPutRequestBody emprestimoPutRequestBody = new EmprestimosPutRequestBody(1l, sdf.parse("2024/05/22"), sdf.parse("2024/06/26"));
	    
	    Mapeador.MAPEADOR.atualizeEmprestimo(emprestimoPutRequestBody, emprestimoSaved);
	    
	    System.out.println("-----------------------------------------");
	    System.out.println("data devolucao = " + sdf.format(emprestimoSaved.getDataDevolucao()));
	    System.out.println("livro titulo = " + emprestimoSaved.getLivros().contains(livro));
	    System.out.println("data devolucao = " + emprestimoSaved.getUsuario().getName());
	    
	  }
	
}



