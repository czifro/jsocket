/*

    Copyright (C) 2015  Czifro Development

    This file is part of the jsock.interfaces package

    The jsock.interfaces package is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    The jsock.interfaces package is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the jsock.interfaces package.  If not, see <http://www.gnu.org/licenses/>.

 */

package jsock.interfaces;

import jsock.crypto.RSA;

/**
 * Created by czifro on 2/2/15. Methods to be implemented to encrypt a socket connection
 */
public interface IEncryptSocket {

    public void encryptConnection(RSA rsa);

    public boolean connectionIsEncrypted();

    public void send_encrypted(byte[] bytes);

    public byte[] recv_encrypted() throws Exception;

}
