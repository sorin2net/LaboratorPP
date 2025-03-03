#!/bin/sh

# Rulează programul Java
echo "Execut programul Java..."
java Main

# Adaugă fișierul citit în Git
git add fisierulmeu.txt
git commit -m "Adăugat fisierul text"

# Adaugă și commit-uiește modificările codului Java
git add Main.java
git commit -m "Adăugat codul sursă"

# Adaugă și commit-uiește scriptul de rulare
git add entrypoint.sh
git commit -m "Adăugat scriptul de rulare"

# Push la repository (dacă ai un repo Git configurat, de exemplu pe GitHub)
# git remote add origin <URL-repository>
# git push -u origin main

echo "Proces complet!"
