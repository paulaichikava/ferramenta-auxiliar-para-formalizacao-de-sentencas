#Introdução do projeto

# Introdução #

O principal meio de comunição e registro de informações desde os
tempos antigos é a linguagem escrita. Por este meio nossa espécie foi capaz
de transmitir informações para as gerações posteriores. Hoje em dia, muita
informação que geramos se encontra armazenada em relatórios e textos. A
proposta desse trabalho é caminhar na direção de estruturar essas informações
para tornar possível o seu processamento por um computador.

A metodologia usada para obter este resultado é a transformação da
linguagem escrita para uma estrutura de dados geradas a partir de tags dados
por uma ferramenta sem^antica e a partir desses dados gerar express~oes lógicas.

Estas express~oes poder~ao ser capazes de inferir asserções sobre o texto. Quanto
mais complexa a linguagem lógica escolhida para essa transformação, maior a
expressividade do texto traduzido sendo possível inferir asserções de forma
mais adequada.

A linguagem lógica escolhida para este trabalho foi a lógica proposicional,
a mais simples disponível. O motivo desta escolha foi tornar o projeto
mais simples servindo como introdução ao problema, facilitando que outros estudantes
usem-o como degrau para o aprendizado e introdução a lógicas mais
complexas.

A lógica proposicional é ensinada em todos os cursos do Departamento
de Informática e utilizada para apresentar aos alunos de Informática as
Capítulo 1. Introdução 10
possibilidades e o poder computacional das ferramentas que é possível criar
usando lógica como ferramenta.
Além da convers~ao para a lógica proposicional, um dos problemas abordados
por este trabalho é a complexidade da interpretação de sentenças em
português.

Para resolver este problema foram usadas duas ferramentas: uma desenvolvida
pelo laboratório LEARN (PUC-RIO), que se chama FE-EXT-WS; e
outra desenvolvida pelo grupo NLX do Departamento de Informática da Universidade
de Lisboa.

Estas ferramentas realizam diversas análises. Dentro da gama de opções
fornecida pelas ferramentas para análise, foi utilizado o POS Tagger, opção
fornecida tanto pelo F-EXT-WS quanto pelo LX Suite. Esta função consegue
enriquecer sentenças em portugu^es associando a cada palavra sua possível
classe gramatical (verbo, advérbio, pronome etc.).

As sentenças enriquecidas por essa análise s~ao usadas como entrada para
o algoritmo responsável por construir as express~oes em lógica proposicional.

Deve-se ressaltar que, quando nos utilizamos de algoritmos para a análise de
linguagem natural n~ao podemos garantir o reconhecimento de todo o conjunto
de express~oes da língua. é preciso utilizar um conjunto de controle para que
seja possível garantir, para esse grupo, o reconhecimento das express~oes e assim
a geração de express~oes lógicas corretas.