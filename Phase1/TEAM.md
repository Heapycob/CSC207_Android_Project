# Contact Information
1. CoCo Lu
    * wenjie.lu@mail.utoronto.ca
    * 437-985-6630
2. Edison Tse
    * tse.edison@gmail.com
    * 647-767-2687
3. Yuchen Tong
    * yuchen.tong@mail.utoronto.ca
    * 289-689-3303
4. Leo Guo
    * jiongtian.guo@mail.utoronto.ca
    * 647-975-6890

## Team Contract

### Meeting Notes
1. Meeting 10/23/2018
    * Maybe need classes: AccountManager, Account, GameCenter, Game
    * GameCenter: contains data and methods pertinent to game operation
        - launching games
        - saving game progress
        - storing games
        - loading games
        - tracking current games
        - setting and preferences for games
    * Account: contains data and methods pertinent to player
        - UserName
        - Password
    * Account Manager: contains data and methods pertinent to accounts
        - Sign up
        - Sign in
        - Links Account and GameCenter
    * Game extends Observable
        - the actual game
    * GameCenter, Account and AccountManager all Serializable
    * Abstract Class ScoreBoard implements Observer
        - Abstract Method addScore
        - Abstract Method updateScore
        - Method getScore
        - Method getPlayerScore
        - ? Method removeScore
    * Each Game's scoreBoard extends ScoreBoard

