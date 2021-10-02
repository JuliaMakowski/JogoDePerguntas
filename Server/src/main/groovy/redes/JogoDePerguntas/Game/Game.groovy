package redes.JogoDePerguntas.Game

import redes.JogoDePerguntas.Game.Questions.QuestionRepository
import redes.JogoDePerguntas.Game.Questions.Questions

class Game {
    def static menu
    def static melhorPontuacao = 0
    static GameMode gameMode

    def Start(Player player){
        player.currentQuestions = null;
        player.finalizado =false
        if (player.mode == GameMode.FACIL){
            player.currentQuestions = QuestionRepository.CreateQuestionsFacil()
        }
        if (player.mode == GameMode.MEDIO){
            player.currentQuestions = QuestionRepository.CreateQuestionsMedio()
        }
        if (player.mode == GameMode.DIFICIL){
            player.currentQuestions = QuestionRepository.CreateQuestionsDificil()
        }
       player.ponto=0
    }
    def static Menu(){
         menu = "  _______ _             _____                      \n" +
                " |__   __| |           / ____|                     \n" +
                "    | |  | |__   ___  | |  __  __ _ _ __ ___   ___ \n" +
                "    | |  | '_ \\ / _ \\ | | |_ |/ _` | '_ ` _ \\ / _ \\\n" +
                "    | |  | | | |  __/ | |__| | (_| | | | | | |  __/\n" +
                "    |_|  |_| |_|\\___|  \\_____|\\__,_|_| |_| |_|\\___|\n" +
                "                                                   \n" +
                "==========================================================\n" +
                "                   1. Jogo Facil\n" +
                "                   2. Jogo Medio\n" +
                "                   3. Jogo Dificil\n" +
                "                   Maior pontuacao:"+melhorPontuacao+"                                    \n"+
                "                   -1. Exit\n"+
                "=========================================================="
    }

    def updateMelhorPontuacao(Player player){
        if (melhorPontuacao<player.ponto) melhorPontuacao=player.ponto
    }

    def getQuestion(Player player){
        if (player.currentQuestions.isEmpty()){
            updateMelhorPontuacao(player)
            player.finalizado = true
            return "O JOGO TERMINOU \n" +
                    "SUA PONTUACAO: "+player.ponto+"\n" +
                    "MELHOR PONTUACAO: "+melhorPontuacao+
                    "\n\n" +
                    "Pressione 0 para voltar ao menu"
        }else {
            player.question = player.currentQuestions.removeFirst()
            return player.question.question + "\n"+ "A. "+ player.question.answers.answers.get("A") +" \n" +
                    "B. "+player.question.answers.answers.get("B") + "\n"+ "C. "+ player.question.answers.answers.get("C") + "\n"+
                    "D. "+ player.question.answers.answers.get("D")
        }
    }
    def VerifyAnswer(String response, Player player){
        if (!player.finalizado){
            if (response.equals(player.question.rightResponse)) {
                if (player.mode  == GameMode.FACIL){
                   player.ponto +=2
                    return "\nVoce recebeu 2 pontos \n" +
                            "SUA PONTUACAO: " + player.ponto+"\n" +
                            "MELHOR PONTUACAO: "+ melhorPontuacao
                }
                if (player.mode  == GameMode.MEDIO){
                   player.ponto +=5
                    return "\nVoce recebeu 5 pontos \n" +
                            "SUA PONTUACAO: "+player.ponto+"\n" +
                            "MELHOR PONTUACAO: "+melhorPontuacao
                }
                if (player.mode  == GameMode.DIFICIL){
                   player.ponto +=10
                    return "\nVoce recebeu 10 pontos \n" +
                            "SUA PONTUACAO: "+player.ponto+"\n" +
                            "MELHOR PONTUACAO: "+melhorPontuacao
                }
            } else return "A resposta esta errada"
        } else {
            getQuestion(player)
        }
    }
}
