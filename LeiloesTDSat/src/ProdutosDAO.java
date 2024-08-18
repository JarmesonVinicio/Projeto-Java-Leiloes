import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {
    conectaDAO conexao;
    Connection conn;
    PreparedStatement prep;
    ResultSet rs;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public ProdutosDAO(){
        this.conexao = new conectaDAO();
        this.conn = this.conexao.connectDB();
    }
    
    public void cadastrarProduto (ProdutosDTO produto){
        String sql = "insert into produtos (nome, valor, status) VALUES (?,?,?)";
        
        try {
            prep = this.conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setString(2, Integer.toString(produto.getValor()));
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: "+ e.getMessage());
        }
        //conn = new conectaDAO().connectDB();
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        String sql = "SELECT * FROM produtos";
        
        try {
            prep = this.conn.prepareStatement(sql);
            rs = prep.executeQuery();  // Executa a consulta e obtém o ResultSet
            
            while(rs.next()){
                ProdutosDTO p = new ProdutosDTO();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getInt("valor"));
                p.setStatus(rs.getString("status"));
                listagem.add(p);
            }
            return listagem;
            
        } catch (Exception e) {
            return null;
        }
        
    }
    
    public void venderProduto (String id){
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, "Vendido");
            stmt.setString(2, id);
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Status do produto atualizado para “Vendido”.", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println("Erro ao editar dados do cliente "+ e.getMessage());
        }
    }
    
    public List <ProdutosDTO> listarProdutosVendidos (){
        String sql = "SELECT * FROM produtos WHERE status = ?";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, "Vendido");
            ResultSet rs = stmt.executeQuery();
            
            List<ProdutosDTO> listaProduto = new ArrayList<>();
            
            while(rs.next()){
                ProdutosDTO p = new ProdutosDTO();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getInt("valor"));
                p.setStatus(rs.getString("status"));
                listaProduto.add(p);
            }
            return listaProduto;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    
        
}

