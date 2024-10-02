package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database;

public class SoundModel {
    private int id;
    private int img;
    private int imgHome;
    private int imgFavorite;
    private int name;
    private int sound;
    private boolean isFavorite;
    private String type;

    public SoundModel(int id, int img,int imgHome,int imgFavorite, int name, int sound, boolean isFavorite, String type) {
        this.id = id;
        this.img = img;
        this.imgHome = imgHome;
        this.imgFavorite = imgFavorite;
        this.name = name;
        this.sound = sound;
        this.isFavorite = isFavorite;
        this.type = type;
    }

    public int getImgFavorite() {
        return imgFavorite;
    }

    public void setImgFavorite(int imgFavorite) {
        this.imgFavorite = imgFavorite;
    }

    public int getImgHome() {
        return imgHome;
    }

    public void setImgHome(int imgHome) {
        this.imgHome = imgHome;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Existing getters and setters
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
