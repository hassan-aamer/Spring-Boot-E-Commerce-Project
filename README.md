Proje Hedefi:
Bu proje, temel bir e-ticaret platformu oluşturmayı amaçlamaktadır. Kullanıcılar, ürünleri görüntüleyebilir, sipariş verebilir, ödeme işlemleri yapabilir ve siparişlerinin durumunu takip edebilirler.

Proje Özellikleri:
Çift Yönlü İlişkiler: Örneğin, User ile Cart, User ile Order arasında bir ilişki bulunmaktadır.
Veritabanı Tasarımı:
User: Kullanıcı bilgileri (ID, isim, email, şifre).
Product: Ürün bilgileri (ID, ad, fiyat, açıklama).
Order: Sipariş bilgileri (ID, kullanıcı ID'si, toplam fiyat, durum).
Payment: Ödeme bilgileri (ID, sipariş ID'si, ödeme miktarı, ödeme yöntemi).
Cart: Kullanıcıların sepeti (ID, ürün listesi, toplam fiyat).

Teknolojiler ve Araçlar:
Spring Boot: Java tabanlı web uygulaması geliştirme framework’ü, RESTful API oluşturmak için kullanıldı.
JPA (Java Persistence API): Veritabanı işlemleri için Entity sınıfları ve ilişkiler oluşturmak amacıyla kullanıldı.
PostgreSQL Database: Veritabanı olarak tercih edildi 
Postman: API'leri test etmek için kullanıldı.
Maven: Proje yönetimi ve bağımlılık yönetimi için kullanıldı.

Proje Mimarisi:
Model: JPA ile tanımlanmış Entity sınıfları (örneğin, User, Product, Order, Cart, vb.).
Controller: RESTful API'ler sağlayan Spring Boot controller sınıfları.
Service: İş mantığı burada bulunur. Veritabanı işlemleri ve iş kuralları burada yönetilir.
Repository: JPA repository interface’leri kullanılarak veritabanı işlemleri yapılır.

Proje Adımları:
* Ürün Yönetimi (Product API)

* Kategori Ekleme
   
* Sepet Yönetimi (Cart)
Sepet Görüntüleme ve Güncelleme (GET, POST): Kullanıcılar sepetindeki ürünleri görüntüleyebilir, ürünleri çıkarabilir veya miktarlarını değiştirebilirler.

* Sipariş Yönetimi (Order Api)
Sipariş Verme (POST): Kullanıcılar, sepetteki ürünleri sipariş verebilir. Sipariş verildikten sonra ödeme işlemi yapılabilir.
Sipariş Durumu (PATCH): Siparişlerin durumu değiştirebilir (örneğin, "pending", "shipped", "delivered").

Kullanıcı Listeleme 
![Ekran Resmi 2025-06-19 21 19 27](https://github.com/user-attachments/assets/abb07c63-2df0-4bb7-809a-557bea9dc2ce)

Ürün Ekleme
![Ekran Resmi 2025-06-19 21 20 28](https://github.com/user-attachments/assets/1c49a8e4-3920-4a6c-9c51-751331850140)

Sipariş Durumu Görüntüleme
![Ekran Resmi 2025-06-19 21 26 19](https://github.com/user-attachments/assets/5965f535-df41-4a28-b765-d3473ab4e72c)




