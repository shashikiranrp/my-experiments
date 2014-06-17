#!/usr/bin/python

import SocketServer, getopt, sys, commands, socket, time

LOCAL_HOST = '127.0.0.1'
DEFAULT_PORT = 5555
LOG_PATH = 'gserv.log'
DEFAULT_CMD = 'echo "\n\nGeneralServer success page\n\n";ps -axj'

FORMAT_HOLDER = '''
<html>
<head>
<title>
GenServer - 0.0
</title>
</head>
<body>
<h2>
GeneralServer Default Format Page
</h2>
<tt>
GenServer listening to port %s in %s (Log file is %s).
</tt>
<p />
<p />
<dt><strong>Command Name:</strong></dt>
<dd>%s</dd>
<dt><strong>Command STDOUT:</strong></dt>
<dd>%s</dd>
<dt><strong>Command Return Status:</strong></dt>
<dd>%s</dd>
</body>
</html>
'''

def ann(msg):
  return "[GenServer]: [ %s ]: %s\n" % (time.asctime(),msg)

class ServerHandler(SocketServer.BaseRequestHandler):
  
  @classmethod
  def init_data(cls,hostname,port,log,cmd,format):
    cls.hostname = hostname
    cls.port = port
    cls.logfile = open(log,"w")
    cls.cmd = cmd
    cls.format = format
    cls.log = log
    cls.logmsg("ServerHandler intialization details host: %s, port: %s, log: %s, cmd: %s"% (str(hostname),str(port),str(log),str(cmd)),True)
  
  
  @classmethod
  def logmsg(cls,msg,wipe=False):
    logfile = open(cls.log,"%s"%("w" if wipe else "a"))
    logfile.write(ann(msg))
    logfile.close()

  def handle(self):
    self.logmsg("Handling request of: %s" % (self.client_address[0]))
    op_str = ""
    cmd_op = commands.getstatusoutput(self.cmd)
    if self.format:
      op_str = FORMAT_HOLDER % (str(self.port),str(self.hostname),str(self.log),str(self.cmd),str(cmd_op[1]),str(cmd_op[0]))
    else:
      op_str = cmd_op[1]
    self.request.send(op_str)
    self.request.close()


def main(opts):
  # Default options and settings
  hostname = socket.gethostbyaddr(LOCAL_HOST)[0]
  port = DEFAULT_PORT
  log_file = LOG_PATH
  cmd = DEFAULT_CMD
  no_format = True
  
  for (o,v) in opts:
    if o == '-h' or o == '--hostname':
      hostname = v
    elif o == '-p' or o == '--port':
      port = int(v)
    elif o == '-l' or o == '--log':
      log_file = v
    elif o == '-c' or o == '--cmd':
      cmd = v
    elif o == '-f' or o == '--format':
      no_format = False
    else:
      usage()
  ServerHandler.init_data(hostname,port,log_file,cmd,not no_format)

  try:
    server = SocketServer.TCPServer((hostname, port), ServerHandler)
    server.serve_forever()
  except KeyboardInterrupt:
    sys.exit(0)  
  except socket.error:
    sys.stderr.write("Dude another port!\n")

def usage():
  sys.stderr.write('''
usage: gen_server [-h|-p|-l|-c|-f]
  -h or --hostname <hostname_of_server> [localhost] => Host name of the server.
  -p or --port <port_no_to_bind> [5555]       => Port number to bind.
  -l or --log <path_to_logfile> [gserv.log]         => out and err Log file of gen_server.
  -c or --cmd <command_to_executed> [ps -axj]       => Command to execute.
  -f or --format             => Need a HTML format.
  -o or --options            => Prints options.
''')
  sys.exit(0)

if __name__=="__main__":
  try:
    main(getopt.getopt(sys.argv[1:],"h:p:l:c:fo",["hostname=","port=","log=","cmd=","format","options"])[0])
  except getopt.GetoptError:
    usage()
