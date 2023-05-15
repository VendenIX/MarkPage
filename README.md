# MarkPage Notes de dev
Application de marque page pour mes lectures
C'est codé en Java et j'utilise Android Studio car y a un émulateur intégré assez pratique pour voir le rendu de l'appli
Pour le stockage de l'application j'ai utilisé une base de donnée SQLite qui assure un stockage en local sur le téléphone. 
L'appli fonctionne pour android pour pratiquement toutes les versions actuelles.
Pour le moment j'ai réalisé la liste des livres, la progress bar, la page d'ajout d'un livre, le fait de pouvoir modifier le titre, l'auteur, et la currentPage via une SeekBar

Éléments que je vais bientôt ajouter:
- Le fait de pouvoir ajouter une liste de mots de vocabulaires afin de pouvoir consulter son propre dictionnaire des mots pour chaque livre donc je vais devoir ajouter une nouvelle colonne a la base de données
- Un bouton assez discret permettant de trier les éléments de la liste selon leur progression, leur nombre de pages, l'auteur
- Faire en sorte que la liste ait une couleur qui change une fois sur deux 
- Ajouter des paramètres pour changer la langue entre Anglais/Francais/Chinois/Espagnol/Allemand/Italien/Japonais
- Ajouter un paramètre pour donner différents themes a l'application : Sombre, Clair, Defaut (Orange) , Personnalisé et genre le personnalisé donnerait un Coler Picker RGB pour mettre la couleur que l'on veut , et on aurait donc 3 couleurs principales:
on aurait les textes, le fond de l'appli, le fond des widgets 
- Ajouter une barre de recherche qui serait caché dans un menu humberger, qui permettrait de rechercher dans la liste avec une barre de recherche 
- Faudrait essayer également de voir comment implémenter de la publicité pour ma prochaine application 


Idées futures mais pas nécéssaires: 
Faire des comptes utilisateurs et connecter l'application pour quelle propose des livres du même auteur ou du même theme qu'un livre que l'on aime bien, mais faudrait voir avec quelle API je fais cela.

#Comment je fais pour télécharger l'application ?
Alors elle n'est pas encore téléchargeable pour le moment car elle n'est toujours pas finis, mais si tu es du genre tryhard, tu peux cloner mon dépôt et l'ouvrir avec Android Studio et générer le fichier APK.

#Je suis tout ouvert à des forks pour d'éventuels ajouts

Venden
