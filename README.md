# Siri

<p align="center">
  <img src="https://images.emojiterra.com/google/noto-emoji/unicode-15/color/512px/1f980.png" />
</p>

![Java](https://img.shields.io/badge/Java-22-blue)  
![Spark](https://img.shields.io/badge/Spark-2.9.2-brightgreen)  
![License](https://img.shields.io/badge/License-MIT-yellow)  

Uma API REST para realizar crawling de páginas web, desenvolvida em Java utilizando o framework Spark. Este projeto permite extrair e processar informações de sites de forma eficiente e escalável.  

## 🚀 Funcionalidades  

- 🔍 **Crawling de páginas web**: Extrai informações de todas os sublinks de uma página especificada, permanecendo no mesmo domínio.  
- 🌐 **API RESTful**: Endpoints para iniciar e gerenciar processos de crawling.  

## 🛡️ Requisitos  

Certifique-se de ter os seguintes softwares instalados no seu ambiente:  

- **Java 22+**  
- **Maven 3.6+**  
- **Git**

Além disso, certifique-se de definir a variável de ambiente **BASE_URL** para informar ao programa qual site será usado como base.

## 🏗️ Como executar o projeto  

1. Clone o repositório:  
   ```bash
   git clone https://github.com/hentzrafael/siri.git
   cd siri
   ```

2. Compile o projeto:  
   ```bash
   mvn clean install
   ```  

3. Inicie a aplicação:  
   ```bash
   java -jar target/siri-1.0-SNAPSHOT.jar
   ```  

4. Acesse a API:  
   A aplicação será iniciada no endereço `http://localhost:4567`.  

## 📚 Endpoints  

### **GET /crawl/:id**  
Verifica o status de um webcrawl com o id especificado.  

**Resposta**:  
```json
{
  "id": "<id>",
  "status": "active",
  "urls":[
      "http://example.com",
    ]
}
```  

### **POST /crawl**  
Inicia um processo de crawling com base na keyword enviada.  

**Parâmetros**:  
- `keyword` (string) - Keyword a ser processada.  

**Exemplo de requisição**:  
```json
{
  "keyword": "example"
}
```  

**Resposta**:  
```json
{
  "id": "abcd1234"
}
```  

## 🧪 Testes  

Execute os testes unitários com o comando:  
```bash
mvn test
```  

## 📜 Licença  

Este projeto é licenciado sob a licença [MIT](LICENSE).  

## ✨ Contribuições  

Contribuições são bem-vindas! Siga as etapas abaixo:  

1. Faça um fork do projeto.  
2. Crie uma branch para sua feature: `git checkout -b minha-feature`.  
3. Realize suas alterações e faça commit: `git commit -m 'Adiciona minha feature'`.  
4. Envie suas alterações: `git push origin minha-feature`.  
5. Abra um Pull Request.  

## 📫 Contato  

Se tiver dúvidas ou sugestões, entre em contato:  
- Email: [hentz.dev@gmail.com](mailto:hentz.dev@gmail.com)  
- LinkedIn: [hentzrafael](https://linkedin.com/in/hentzrafael)  
```  
