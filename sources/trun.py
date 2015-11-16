#
# trun
from argparse import ArgumentParser
from os.path import realpath
import subprocess as sp
import json
import sys
import os


def liveStream(command):
  proc = sp.Popen( command, stdout=sp.PIPE )
  while True:
    ln = proc.stdout.readline()
    if( ln=='' and proc.poll() != None ): break
    if ln: print ln.strip()
  retval = proc.poll( )
  if retval != 0:
    print '[ERROR]: %s returned non-zero exit status -- %s' % (' '.join(command), retval)
 

def argumentsOf(argv):
  parser = ArgumentParser( description='Command Line lifecycle management script for Test Reporter.' )
  parser.add_argument( '-t', '--target', type=str, default='testReporter' )
  parser.add_argument( '--clean', action='store_true' )
  parser.add_argument( '--build', action='store_true' )
  parser.add_argument( '--config-file', type=str, default='./etc/env/trun.json' \
                      ,metavar='FILE-PATH' )
  return parser.parse_args( argv )


def configOf(args):
  path = realpath( args.config_file )
  with open( path, 'rb' ) as fp:
    return json.load( fp )


def makeNonExistentJar(args, config):
  jar_path = '../target/%s-jar-with-dependencies.jar' % args.target
  if args.clean:
    print '[INFO] Cleaining \'%s\'' % jar_path
    os.remove( jar_path )

  maven = [ 'mvn', 'compile', 'assembly:single' ]
  liveStream( maven )
  return jar_path


def runInJVM(jar, config):
  cmd = [ 'java', '-jar', jar, '>&0' ]
  liveStream( cmd )


def main(argc, argv):
  args = argumentsOf(argv[ 1: ])
  config = configOf( args )
  jar_path = makeNonExistentJar( args, config ) 
  if not( args.build ):
    runInJVM( jar_path, config )


if __name__=='__main__':
  argv = sys.argv
  argc = len(argv)
  main( argc, argv )

