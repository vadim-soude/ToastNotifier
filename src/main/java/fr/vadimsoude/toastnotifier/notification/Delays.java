package fr.vadimsoude.toastnotifier.notification;

public record Delays(Integer fadeIn, Integer stay, Integer fadeOut) {
    public long getTotal(){
        return this.fadeIn + this.stay + this.fadeOut;
    }
}
