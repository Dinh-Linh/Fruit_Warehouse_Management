package generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.Random;

public class IdTaiKhoanGenerator implements IdentifierGenerator {

    //Tạo mã tài khoản cấp 3 ký tự đầu TK-, 8 ký tự sau ngẫu nhiên
    //Ví dụ: TK-11111111

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        String prefix = "TK-";
        String randomPart = String.format("%08d", new Random().nextInt(100000000));
        return prefix + randomPart;
    }
}
