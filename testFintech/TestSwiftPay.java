import Dto.Request.*;
import Fintech.Data.Model.SwiftPay;
import Fintech.Data.Repository.SwiftRepo;
import Fintech.Services.SwiftPayService;
import Fintech.Services.SwiftService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSwiftPay {

    SwiftPayService swiftService = new SwiftService();
    SwiftRepo swiftRepo;


    @Test
    public void testWeCanCreateAccount() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setFirstName("Edmund");
        createAccountRequest.setLastName("Udeh");
        createAccountRequest.setPhoneNumber("08144043783");
        createAccountRequest.setPin("1010");
        SwiftPay swiftPay = swiftService.register(createAccountRequest);
        System.out.println(swiftPay.getAccountName());
        System.out.println(swiftPay.getAccountNumber());
        System.out.println(swiftPay.getBalance());
        System.out.println(swiftService.count());
        assertEquals(1, swiftService.count());
    }

    @Test
    public void testForDeposit() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setFirstName("Edmund");
        createAccountRequest.setLastName("Udeh");
        createAccountRequest.setPhoneNumber("08144043783");
        createAccountRequest.setPin("1010");

        swiftService.register(createAccountRequest);
        assertEquals(1, swiftService.count());

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber("8144043783");
        depositRequest.setAmount(50_000);

        swiftService.deposit(depositRequest);

        CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
        checkBalanceRequest.setAccountNumber("8144043783");
        checkBalanceRequest.setPin("1010");
        Double balanceReturned = swiftService.checkBalance(checkBalanceRequest);



        assertEquals(50_000, balanceReturned);


    }

    @Test
    public void testForWithdraw() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setFirstName("Edmund");
        createAccountRequest.setLastName("Udeh");
        createAccountRequest.setPhoneNumber("08144043783");
        createAccountRequest.setPin("1010");

        swiftService.register(createAccountRequest);
        assertEquals(1, swiftService.count());

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber("8144043783");
        depositRequest.setAmount(50_000);

        swiftService.deposit(depositRequest);

        CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
        checkBalanceRequest.setAccountNumber("8144043783");
        checkBalanceRequest.setPin("1010");
        Double balanceReturned = swiftService.checkBalance(checkBalanceRequest);
        System.out.println(balanceReturned);

        assertEquals(50_000, balanceReturned);

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAmount(30_000);
        withdrawRequest.setPin("1010");
        withdrawRequest.setAccountNumber("8144043783");
        swiftService.withdraw(withdrawRequest);


        checkBalanceRequest.setAccountNumber("8144043783");
        checkBalanceRequest.setPin("1010");
        Double finalBalance = swiftService.checkBalance(checkBalanceRequest);

        assertEquals(20_000, finalBalance);

        System.out.println(swiftService.displayAllTransaction());

    }

    @Test
    public void testWeCanTransfer() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setFirstName("Edmund");
        createAccountRequest.setLastName("Udeh");
        createAccountRequest.setPhoneNumber("08144043783");
        createAccountRequest.setPin("1010");

        SwiftPay swiftPay = swiftService.register(createAccountRequest);
        System.out.println(swiftPay.getAccountNumber());
        assertEquals(1, swiftService.count());

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber("8144043783");
        depositRequest.setAmount(50_000);

        swiftService.deposit(depositRequest);

        CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
        checkBalanceRequest.setAccountNumber("8144043783");
        checkBalanceRequest.setPin("1010");
        Double balanceReturned = swiftService.checkBalance(checkBalanceRequest);
        System.out.println(balanceReturned);

        assertEquals(50_000, balanceReturned);

        CreateAccountRequest createAccountRequest1 = new CreateAccountRequest();
        createAccountRequest1.setFirstName("Ashley");
        createAccountRequest1.setLastName("Ndabai");
        createAccountRequest1.setPhoneNumber("08151209182");
        createAccountRequest1.setPin("5050");

        SwiftPay swiftPay1 = swiftService.register(createAccountRequest1);
        System.out.println(swiftPay1.getAccountNumber());
        System.out.println("Ashley initial balance" + " "+ swiftPay1.getBalance());
        System.out.println();

        assertEquals(2, swiftService.count());

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setPin("1010");
        transferRequest.setSenderAccountNumber("8144043783");
        transferRequest.setAmount(20_000);
        transferRequest.setReceiverAccountNumber("8151209182");
        swiftService.transfer(transferRequest);


        CheckBalanceRequest checkBalanceRequest1 = new CheckBalanceRequest();
        checkBalanceRequest1.setAccountNumber("8144043783");
        checkBalanceRequest1.setPin("1010");
        Double balanceAfterTransfer = swiftService.checkBalance(checkBalanceRequest1);



        assertEquals(30_000, balanceAfterTransfer);


        CheckBalanceRequest checkBalanceRequest2 = new CheckBalanceRequest();
        checkBalanceRequest2.setAccountNumber(swiftPay1.getAccountNumber());
        checkBalanceRequest2.setPin(createAccountRequest1.getPin());
        Double balanceAfterTransfer2 = swiftService.checkBalance(checkBalanceRequest2);

        System.out.println();
        System.out.println("Ashley's account after the transfer" + " "+ swiftPay1.getBalance());
        System.out.println();



        assertEquals(20_000, balanceAfterTransfer2);
        System.out.println(swiftService.displayAllTransaction());

    }

    @Test
    public void testYouCanUpdatePin() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setFirstName("Edmund");
        createAccountRequest.setLastName("Udeh");
        createAccountRequest.setPhoneNumber("08144043783");
        createAccountRequest.setPin("1010");
        SwiftPay swiftPay = swiftService.register(createAccountRequest);
        System.out.println(swiftPay.getAccountName());
        System.out.println(swiftPay.getAccountNumber());
        System.out.println(swiftPay.getBalance());
        System.out.println(swiftService.count());
        assertEquals(1, swiftService.count());
        UpdatePinRequest updatePinRequest = new UpdatePinRequest();
        updatePinRequest.setOldPin("1010");
        updatePinRequest.setNewPin("3030");
        updatePinRequest.setAccountNumber("8144043783");
        swiftService.updatePin(updatePinRequest);
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber("8144043783");
        depositRequest.setAmount(50_000);
        swiftService.deposit(depositRequest);
        CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
        checkBalanceRequest.setAccountNumber("8144043783");
        checkBalanceRequest.setPin("3030");
        Double balanceReturned = swiftService.checkBalance(checkBalanceRequest);
        System.out.println(balanceReturned);
        assertEquals(50_000, balanceReturned);

    }
    @Test
    public void testToDisplayAllTransaction(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setFirstName("Edmund");
        createAccountRequest.setLastName("Udeh");
        createAccountRequest.setPhoneNumber("08144043783");
        createAccountRequest.setPin("1010");

        SwiftPay swiftPay = swiftService.register(createAccountRequest);
        System.out.println(swiftPay.getAccountNumber());
        assertEquals(1, swiftService.count());

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber("8144043783");
        depositRequest.setAmount(50_000);

        swiftService.deposit(depositRequest);

        CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
        checkBalanceRequest.setAccountNumber("8144043783");
        checkBalanceRequest.setPin("1010");
        Double balanceReturned = swiftService.checkBalance(checkBalanceRequest);
        System.out.println("my first balance after deposit " + balanceReturned);
        System.out.println();

        assertEquals(50_000, balanceReturned);

        CreateAccountRequest createAccountRequest1 = new CreateAccountRequest();
        createAccountRequest1.setFirstName("Ashley");
        createAccountRequest1.setLastName("Ndabai");
        createAccountRequest1.setPhoneNumber("08151209182");
        createAccountRequest1.setPin("5050");

        SwiftPay swiftPay1 = swiftService.register(createAccountRequest1);
        System.out.println(swiftPay1.getAccountName());
        System.out.println(swiftPay1.getAccountNumber());
        System.out.println("Ashley first balance" + " " + swiftPay1.getBalance());
        System.out.println();

        assertEquals(2, swiftService.count());

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSenderPin("1010");
        transferRequest.setSenderAccountNumber("8144043783");
        transferRequest.setAmount(20_000);
        transferRequest.setReceiverAccountNumber("8151209182");

        swiftService.transfer(transferRequest);
        System.out.println();




        CheckBalanceRequest checkBalanceRequest1 = new CheckBalanceRequest();
        checkBalanceRequest1.setAccountNumber("8144043783");
        checkBalanceRequest1.setPin("1010");
        Double balanceAfterTransfer = swiftService.checkBalance(checkBalanceRequest1);
        System.out.println();
        System.out.println("my second balance after transfer" + " "+ balanceAfterTransfer);

        assertEquals(30_000, balanceAfterTransfer);

        CheckBalanceRequest checkBalanceRequest2 = new CheckBalanceRequest();
        checkBalanceRequest2.setAccountNumber("8151209182");
        checkBalanceRequest2.setPin("5050");
        Double balanceAfterTransfer2 = swiftService.checkBalance(checkBalanceRequest2);
        System.out.println();
        System.out.println("Ashley balance after transfer" + " " + balanceAfterTransfer2);

        assertEquals(20_000, balanceAfterTransfer2);

        CreateAccountRequest createAccountRequest2 = new CreateAccountRequest();
        createAccountRequest2.setFirstName("Moyin");
        createAccountRequest2.setLastName("OluwaChineke");
        createAccountRequest2.setPhoneNumber("08064557709");
        createAccountRequest2.setPin("7070");

        SwiftPay swiftPay2 = swiftService.register(createAccountRequest2);
        System.out.println();
        System.out.println(swiftPay2.getAccountName());
        System.out.println(swiftPay2.getAccountNumber());
        System.out.println("Moyin first balance" + " " +swiftPay2.getBalance());

        assertEquals(3, swiftService.count());

        TransferRequest transferRequest1 = new TransferRequest();
        transferRequest1.setSenderPin("1010");
        transferRequest1 .setSenderAccountNumber("8144043783");
        transferRequest1.setAmount(10_000);
        transferRequest1.setReceiverAccountNumber("8064557709");
        System.out.println();
        swiftService.transfer(transferRequest1);
        System.out.println();



        CheckBalanceRequest checkBalanceRequest3 = new CheckBalanceRequest();
        checkBalanceRequest3.setAccountNumber("8144043783");
        checkBalanceRequest3.setPin("1010");
        double balanceAfterTransfer3 = swiftService.checkBalance(checkBalanceRequest3);
        System.out.println();
        System.out.println("my balance after transfer to Moyin" + " "+ balanceAfterTransfer3);

        assertEquals(20_000, balanceAfterTransfer3);

        CheckBalanceRequest checkBalanceRequest4= new CheckBalanceRequest();
        checkBalanceRequest4.setAccountNumber("8064557709");
        checkBalanceRequest4.setPin("7070");
        double balanceAfterTransfer4 = swiftService.checkBalance(checkBalanceRequest4);
        System.out.println();
        System.out.println("Moyin's balance after transfer" + " "+ swiftPay2.getBalance());

        assertEquals(10_000, balanceAfterTransfer4);
        System.out.println();
        System.out.println(swiftService.displayAllTransaction());
        System.out.println();


    }
}




