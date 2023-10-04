**Teste de cadastro de cliente:**  
-Em branco  
-Entrada válida  
  
**Teste de consulta de cliente e seus aluguéis:**  
-Em branco  
-ID inexistente  
-Nome inexistente  
-Nome escrito de forma diferente (acento, maiúsculas e minúsculas)  
-Dois clientes com o mesmo nome  
-Entrada válida  
  
**Teste de cadastro de equipamento:**  
-Em branco  
-Preço negativo  
-Preço R$0,00  
-Qualquer outro caractere a não ser S e N (ao perguntar se é prioritário)  
-Entrada válida  
  
**Teste de consulta de equipamento:**  
-Em branco  
-ID inexistente  
-Nome inexistente  
-Nome escrito de maneira diferente (acento, maiúsculas e minúsculas)  
-Nome com menos de 5 caracteres  
-Entrada válida  
  
**Teste de cadastro de aluguel:**  
-Em branco  
-Caracteres não-numéricos na data  
-Data inválida (exemplo: dia 32, mês 13)  
-Data muito distante (no futuro e/ou passado)  
-Data de fim menor do que data de início  
-Exceder o número possível de dias para alugar  
-Equipamento já alugado no período  
-ID do cliente inexistente  
-ID do equipamento inexistente  
-Entrada válida  

**Teste de consulta de aluguel:**  
-Em branco  
-ID inexistente  
-Caracteres inválidos  
-Entrada válida  
  
**Teste de geração de relatório mensal de aluguéis:**  
-Em branco  
-Caracteres não-numéricos na data  
-Data inválida (exemplo:, mês 13)  
-Data muito distante (no futuro e/ou passado)  
-Entrada válida
