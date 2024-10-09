package bitc.fullstack405.finalprojectspringboot.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class QRCodeGenerator {

    public void generate(Long eventId, Long scheduleId, Long userId, File file) throws Exception {
        String qrText = eventId + "-" + scheduleId + "-" + userId;
        int width = 300;
        int height = 300;

        // QR 코드 생성에 필요한 옵션 설정 (문자 인코딩)
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // QRCodeWriter를 사용하여 QR 코드 생성
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height, hintMap);

        // 파일 시스템 경로를 사용하여 파일 생성
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", file.toPath());
    }
}