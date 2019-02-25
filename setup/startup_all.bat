@ECHO OFF
start "source" cmd.exe /c call run_customer_api.bat
start "flow" cmd.exe /c call run_tokenizer.bat
start "proof" cmd.exe /c call run_consumer.bat