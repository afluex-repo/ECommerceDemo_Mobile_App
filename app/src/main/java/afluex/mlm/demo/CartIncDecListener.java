package afluex.mlm.demo;

public interface CartIncDecListener {

    void getCardItemQuantity(String productInfo, String vendorId, String type);

    void removeCartItem(String cartId);
}
