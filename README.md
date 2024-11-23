# Plant-Tracker
Program służący do przechowywania danych o roślinach ułatwiający ich dokładną pielęgnację 


# O projekcie (backend)
Backend działa na linku http://localhost:8080

Aby uruchomić backend potrzebny jest Docker. Aby ściągnąć wymagany kontener z odpowiednią konfiguracją, proszę w terminalu wydać tą komendę:

```
docker run --name postgres-container -e POSTGRES_DB=plant_tracker_db -e POSTGRES_USER=root -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres
```

Teorytycznie teraz nie powinno być problemu z bazą danych, ale możliwy błąd może pojawić się z wersją javy na Waszych kompach. U siebie mam wersję 19, ale we możecie mieć np. 21 itd. Należy to zmienić w pliku build.gradle

```
languageVersion = JavaLanguageVersion.of(19)   
```

Backend potrzebuje autoryzacji (podstawowe logowanie). Jedyny użytkonik jak na razie to:
username: john.doe@example.com"
password: password123

Po zalogowaniu użytkownik może:

1. Wyświetlić wszystkie roślinki użytkownika
```
http://localhost:8080/plants/{userId}
``` 
Należy zwrócić uwagę, że zwracany userId jest losowo generowany. Endpoint wtedy zwraca:

```
[
    {
        "id": 21,
        "name": "Ficus Benjamin",
        "description": "A lovely ficus.",
        "species": {
            "id": 21,
            "name": "Ficus"
        },
        "lastWatered": "2024-11-21T21:34:22.540085",
        "created": "2024-11-23T21:34:22.540085",
        "lastEvents": [],
        "lastActions": []
    },
    ...
```
2. Zwrócenie species:

```
http://localhost:8080/plants/species
```
też w formie tablicy

3. Dodanie roślinki dla użytkownika to metoda post:

```
http://localhost:8080/plants/{userId}
```
która zwróci stringa: Plant added successfully!


4. Wyświetlić wszystkich użytkowników przez endpoint:

```
http://localhost:8080/users
``` 
Ten endpoint jest bardziej kontrolny i nieużywalny przez frontend
Wówczas zostanie zwrócona tablica:

```
[
    {
        "id": 11,
        "name": "John Doe",
        "password": "$2a$10$b7vN9hT9MDW03yY4vocsXOxyLnVJGvi2OAfvLlQ2JuIA73tYC0IL2",
        "email": "john.doe@example.com",
        "userPlants": [
            {
                "id": 23,
                "name": "Ficus Mary",
                "description": "A vicious ficus.",
                "species": {
                    "id": 21,
                    "name": "Ficus"
                },
                "lastWatered": null,
                "created": null,
                "lastEvents": [],
                "lastActions": []
            },
            {
                "id": 21,
                "name": "Ficus Benjamin",
                "description": "A lovely ficus.",
                "species": {
                    "id": 21,
                    "name": "Ficus"
                },
                "lastWatered": "2024-11-21T21:34:22.540085",
                "created": "2024-11-23T21:34:22.540085",
                "lastEvents": [],
                "lastActions": []
            },
            {
                "id": 22,
                "name": "Desert Cactus",
                "description": "A sturdy cactus.",
                "species": {
                    "id": 22,
                    "name": "Cactus"
                },
                "lastWatered": "2024-11-16T21:34:22.546502",
                "created": "2024-11-23T21:34:22.546502",
                "lastEvents": [],
                "lastActions": []
            }
        ],
        "enabled": true,
        "username": "john.doe@example.com",
        "accountNonExpired": true,
        "credentialsNonExpired": true,
        "accountNonLocked": true,
        "authorities": []
    }
]
```
