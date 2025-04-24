# Go Fish Kart Oyunu

Java ile geliştirilmiş, sadece Yığın (Stack) ve Kuyruk (Queue) veri yapılarını kullanan bir Go Fish kart oyunu uygulaması.

## Oyun Hakkında

Bu proje, klasik Go Fish kart oyununun modifiye edilmiş bir versiyonudur. Oyuncular kart toplayarak setler oluşturmaya çalışır ve en yüksek puanı elde etmeye çalışır.

### Özellikler

- Stack ve Queue veri yapılarının saf implementasyonu
- İnsan oyuncu ve AI rakip
- Özel aksiyon kartları (Joker, Tekrar, Pas, Çöp Geri Kazanım)
- Puan sistemi ve Yüksek Skor tablosu
- Detaylı oyun gösterimi ve tur bilgilendirmeleri

## Oyun Kuralları

1. Her oyuncu 7 kart ile başlar
2. Her turda, bir oyuncu rakibinden elindeki bir kartın aynısından isteyebilir
3. Eğer rakip istenilen karta sahipse, tüm eşleşen kartları verir
4. Eğer rakip istenilen karta sahip değilse, oyuncu desteden kart çeker
5. Aynı değere sahip 4 kart toplayan oyuncu bir set oluşturur ve 10 puan kazanır
6. Eğer bir oyuncunun elinde 9'dan fazla kart olursa, fazla kartları atmak zorundadır ve 10 puan kaybeder
7. Özel kartlar:
   - **J (Joker)**: 5 puan kazandırır ve rakipten herhangi bir kart istenebilir
   - **R (Tekrar)**: 5 puan kazandırır ve bir tur daha oynanır
   - **S (Pas)**: 5 puan kaybettirir ve sıra rakibe geçer
   - **W (Çöp Geri Kazanım)**: 5 puan kazandırır ve bir sonraki turda çöp yığınından kart alınabilir

## Nasıl Oynanır

1. Projeyi derleyin
2. Oyunu başlatın
3. İstenildiğinde adınızı girin
4. Sıra size geldiğinde rakipten istediğiniz kartı seçin
5. Deste bittiğinde veya tüm setler toplandığında oyun sona erer

## Teknik Detaylar

Bu uygulama şu sınıflardan oluşmaktadır:
- `Game`: Ana oyun mantığı
- `Player`: Oyuncu işlemleri
- `AIPlayer`: Bilgisayar oyuncusu stratejisi
- `Card`: Kart nesnesi
- `Stack`: Kart destesi ve atılan kartlar için yığın veri yapısı
- `Queue`: Oyun destesi için kuyruk veri yapısı
- `HighScoreTable`: Yüksek skor kaydı

Tüm veri yapıları (Stack ve Queue) sıfırdan geliştirilmiş olup, Java'nın yerleşik koleksiyon sınıfları kullanılmamıştır.

## Geliştirme

Bu proje, veri yapıları ve nesne yönelimli programlama prensiplerini göstermek amacıyla geliştirilmiştir. Kart oyunu mantığı, kullanıcı arayüzü ve yapay zeka stratejileri üzerine pratik bir uygulamadır.

---

# Go Fish Card Game

A Go Fish card game implementation developed in Java, using only Stack and Queue data structures.

## About the Game

This project is a modified version of the classic Go Fish card game. Players try to collect cards to form sets and achieve the highest score.

### Features

- Pure implementation of Stack and Queue data structures
- Human player and AI opponent
- Special action cards (Joker, Repeat, Skip, Wild Recovery)
- Scoring system and High Score table
- Detailed game display and turn information

## Game Rules

1. Each player starts with 7 cards
2. In each turn, a player can request a card of the same type from their opponent
3. If the opponent has the requested card, they give all matching cards
4. If the opponent does not have the requested card, the player draws from the deck
5. A player who collects 4 cards of the same value forms a set and earns 10 points
6. If a player has more than 9 cards in their hand, they must discard excess cards and lose 10 points
7. Special cards:
   - **J (Joker)**: Earns 5 points and allows requesting any card from the opponent
   - **R (Repeat)**: Earns 5 points and gives an extra turn
   - **S (Skip)**: Loses 5 points and skips the turn
   - **W (Wild Recovery)**: Earns 5 points and allows retrieving a card from the discard pile in the next turn

## How to Play

1. Compile the project
2. Start the game
3. Enter your name when prompted
4. When it's your turn, select a card to request from your opponent
5. The game ends when the deck is empty or all sets have been collected

## Technical Details

This application consists of the following classes:
- `Game`: Main game logic
- `Player`: Player operations
- `AIPlayer`: Computer player strategy
- `Card`: Card object
- `Stack`: Stack data structure for card deck and discarded cards
- `Queue`: Queue data structure for game deck
- `HighScoreTable`: High score record

All data structures (Stack and Queue) are developed from scratch, without using Java's built-in collection classes.

## Development

This project was developed to demonstrate data structures and object-oriented programming principles. It is a practical application of card game logic, user interface, and artificial intelligence strategies. 