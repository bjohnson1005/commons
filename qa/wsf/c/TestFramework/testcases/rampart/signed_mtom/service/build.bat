cl.exe /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "AXIS2_DECLARE_EXPORT"  /D "AXIS2_SVR_MULTI_THREADED" /w /nologo /I%WSFC_HOME%\include /D "NDEBUG" /O2 *.c /link /nologo /LIBPATH:%WSFC_HOME%\lib *.obj axutil.lib axiom.lib axis2_parser.lib axis2_engine.lib neethi.lib axis2_http_receiver.lib  axis2_http_sender.lib /DLL /OUT:signed_mtom.dll

mkdir %WSFC_HOME%\services\signed_mtom
copy signed_mtom.dll %WSFC_HOME%\services\signed_mtom
copy services.xml %WSFC_HOME%\services\signed_mtom